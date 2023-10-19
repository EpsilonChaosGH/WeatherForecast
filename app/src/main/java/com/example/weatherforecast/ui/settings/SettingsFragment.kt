package com.example.weatherforecast.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherforecast.R
import com.example.weatherforecast.utils.collectFlow
import com.example.weatherforecast.databinding.FragmentSettingsBinding
import com.example.weatherforecast.model.SupportedLanguage
import com.example.weatherforecast.model.Units
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::class.java)

    val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SettingSpinnerAdapter(
            requireContext(),
            SupportedLanguage.getLanguageSpinnerItem()
        ).also { adapter ->
            binding.langSpinner.adapter = adapter
        }

        SettingSpinnerAdapter(requireContext(), Units.getUnitsSpinnerItem()).also { adapter ->
            binding.unitsSpinner.adapter = adapter
        }

        binding.langSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val language = SupportedLanguage.getLanguageValue(pos)
                if (viewModel.state.value.selectedLanguage != language) {
                    Log.e("aaaSettingsFrag", language)
                    viewModel.setLanguage(language)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.unitsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val units = Units.getUnitsValue(pos)
                if (viewModel.state.value.selectedUnits != units) {
                    Log.e("aaaSettingsFragU", units)
                    viewModel.setUnits(units)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        observeSettingsState()
    }

    private fun observeSettingsState() = with(binding) {
        collectFlow(viewModel.state) { state ->
            langSpinner.setSelection(SupportedLanguage.getIndex(state.selectedLanguage))
            unitsSpinner.setSelection(Units.getIndex(state.selectedUnits))
            versionTextView.text = state.versionInfo
        }
    }
}