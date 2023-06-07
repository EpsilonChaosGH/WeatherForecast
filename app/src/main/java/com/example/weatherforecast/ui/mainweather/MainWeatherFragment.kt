package com.example.weatherforecast.ui.mainweather

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherforecast.R
import com.example.weatherforecast.collectFlow
import com.example.weatherforecast.databinding.FragmentMainWeatherBinding
import com.example.weatherforecast.mappers.toWeatherState
import com.example.weatherforecast.models.WeatherState
import com.example.weatherforecast.models.isError
import com.example.weatherforecast.models.isSucceeded
import com.example.weatherforecast.models.success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainWeatherFragment : Fragment(R.layout.fragment_main_weather) {

    private val binding by viewBinding(FragmentMainWeatherBinding::class.java)

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMainState()
    }

    private fun observeMainState() {
        collectFlow(viewModel.state) { mainWeatherState ->
            when {
                mainWeatherState.isSucceeded -> {
                    mainWeatherState.success {
                        setWeatherState(it.toWeatherState())
                    }
                }

                mainWeatherState.isError -> {
                    Toast.makeText(requireContext(), "ERROR WEATHER LOADING", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    binding.progressBar.isVisible = true
                }

            }
        }
    }

    private fun setWeatherState(weatherState: WeatherState) {
        with(binding) {
            cityNameTextView.text = weatherState.city
            temperatureTextView.text = weatherState.temperature
            currentWeatherTextView.text = weatherState.description
            currentDateTextView.text = weatherState.data
            feelsLikeTextView.text = weatherState.feelsLike
            humidityTextView.text = weatherState.humidity
            pressureTextView.text = weatherState.pressure
            windSpeedTextView.text = weatherState.windSpeed
            weatherIconImageView.setImageResource(weatherState.weatherType.iconResId)

            if (weatherState.isFavorites) favoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
            else favoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}