package com.example.weatherforecast.ui.mainweather

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.weatherforecast.R
import com.example.weatherforecast.collectEventFlow
import com.example.weatherforecast.collectFlow
import com.example.weatherforecast.databinding.FragmentMainWeatherBinding
import com.example.weatherforecast.entity.AirState
import com.example.weatherforecast.entity.WeatherState
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainWeatherFragment : Fragment(R.layout.fragment_main_weather) {

    private val binding by viewBinding(FragmentMainWeatherBinding::class.java)

    private val viewModel by viewModels<WeatherViewModel>()

    private val adapter = ForecastAdapter()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private val requestLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotLocationPermissionResult
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            refreshLayout.setColorSchemeResources(R.color.main_text_color)
            refreshLayout.setProgressBackgroundColorSchemeResource(R.color.main_color)
            refreshLayout.setOnRefreshListener {


            }
            searchByCoordinatesImageView.setOnClickListener { getWeatherByCoordinates() }
        }

        observeEditorActionListener()
        observeMainState()
        observeEvents()
    }

    private fun observeEditorActionListener() {
        binding.cityEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try {
                    if (binding.cityEditText.text?.isBlank() == true) {
                        throw RuntimeException(getString(R.string.error_404_city_not_found))
                    } else {
                        viewModel.getMainWeatherByCity(
                            City(binding.cityEditText.text.toString())
                        )
                    }
                    return@OnEditorActionListener true
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
            false
        })
    }

    private fun observeEvents() {
        collectEventFlow(viewModel.showMessageResEvent) { massage ->
            Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeMainState() {
        collectFlow(viewModel.state) { mainWeatherState ->
            setAirState(mainWeatherState.airState)
            setWeatherState(mainWeatherState.weatherState)
            adapter.forecastList = mainWeatherState.forecastState
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

    private fun getWeatherByCoordinates() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val coordinates = Coordinates(
                        lat = location.latitude.toString(),
                        lon = location.longitude.toString()
                    )
                    viewModel.getMainWeatherByCoordinates(coordinates)
                } else {
                    viewModel.showMessage(R.string.error_gps_not_found)
                }
            }
    }

    private fun onGotLocationPermissionResult(granted: Boolean) {
        if (granted) {
            viewModel.showMessage(R.string.permission_grated)
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                askUserForOpeningAppSettings()
            } else {
                viewModel.showMessage(R.string.permissions_denied)
            }
        }
    }

    private fun askUserForOpeningAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireActivity().packageName, null)
        )
        if (requireActivity().packageManager.resolveActivity(
                appSettingsIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            viewModel.showMessage(R.string.permissions_denied_forever)
        } else {

            val listener = DialogInterface.OnClickListener { _, _ -> }
            val listenerSettings = DialogInterface.OnClickListener { _, _ ->
                startActivity(appSettingsIntent)
            }
            val builder = AlertDialog.Builder(requireContext())
                .setPositiveButton(R.string.button_open_settings, listenerSettings)
                .setNeutralButton(R.string.button_cancel, listener)
                .create()
            builder.setView(layoutInflater.inflate(R.layout.dialog_gps_settings, null))
            builder.show()
        }
    }
}