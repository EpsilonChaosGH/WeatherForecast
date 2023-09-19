package com.example.weatherforecast.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherforecast.R
import com.example.weatherforecast.collectFlow
import com.example.weatherforecast.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment() : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::class.java)

    val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.langSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.unitsSpinner.adapter = adapter
        }

        binding.langSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if (viewModel.state.value.selectedLanguageIndex != pos) {
                    Toast.makeText(requireContext(), pos.toString(), Toast.LENGTH_SHORT).show()
                    viewModel.setLanguageIndex(pos)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.unitsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                if (viewModel.state.value.selectedUnitIndex != pos) {
                    Toast.makeText(requireContext(), pos.toString(), Toast.LENGTH_SHORT).show()
                    viewModel.setUnitsIndex(pos)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        observeSettingsState()
    }

    private fun observeSettingsState() = with(binding) {
        collectFlow(viewModel.state) { state ->
            langSpinner.setSelection(state.selectedLanguageIndex)
            unitsSpinner.setSelection(state.selectedUnitIndex)
            versionTextView.text = state.versionInfo
        }
    }
}