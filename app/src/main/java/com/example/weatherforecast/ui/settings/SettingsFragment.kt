package com.example.weatherforecast.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment(): Fragment(R.layout.fragment_settings) {

    val binding by viewBinding(FragmentSettingsBinding::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}