package com.example.weatherforecast.ui.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentWeatherBinding

class WeatherFragment: Fragment(R.layout.fragment_weather) {

    private val binding by viewBinding(FragmentWeatherBinding::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}