package com.example.weatherforecast.ui.mainweather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.isError
import com.example.domain.isSucceeded
import com.example.domain.success
import com.example.weatherforecast.R
import com.example.weatherforecast.collectEventFlow
import com.example.weatherforecast.collectFlow
import com.example.weatherforecast.databinding.FragmentMainWeatherBinding
import com.example.weatherforecast.entity.AirState
import com.example.weatherforecast.mappers.toWeatherState
import com.example.weatherforecast.entity.WeatherState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainWeatherFragment : Fragment(R.layout.fragment_main_weather) {

    private val binding by viewBinding(FragmentMainWeatherBinding::class.java)

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMainState()
        observeEvents()
    }

    private fun observeEvents() {
        collectEventFlow(viewModel.showErrorMessageResEvent) { massage ->
            Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeMainState() {
        collectFlow(viewModel.state) { mainWeatherState ->
            setAirState(mainWeatherState.airState)
            setWeatherState(mainWeatherState.weatherState)
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

    @SuppressLint("ResourceType")
    private fun setAirState(airState: AirState) {
        with(binding) {
            valueNO2.text = airState.no2
            qualityNO2.text = getString(airState.no2Quality.qualityResId)
            qualityNO2.setTextColor(
                ContextCompat.getColor(requireContext(), airState.no2Quality.colorResId)
            )

            valueO3.text = airState.o3
            qualityO3.text = getString(airState.o3Quality.qualityResId)
            qualityO3.setTextColor(
                ContextCompat.getColor(requireContext(), airState.o3Quality.colorResId)
            )

            valuePM10.text = airState.pm10
            qualityPM10.text = getString(airState.pm10Quality.qualityResId)
            qualityPM10.setTextColor(
                ContextCompat.getColor(requireContext(), airState.pm10Quality.colorResId)
            )

            valuePM25.text = airState.pm25
            qualityPM25.text = getString(airState.pm25Quality.qualityResId)
            qualityPM25.setTextColor(
                ContextCompat.getColor(requireContext(), airState.pm25Quality.colorResId)
            )
        }
    }
}