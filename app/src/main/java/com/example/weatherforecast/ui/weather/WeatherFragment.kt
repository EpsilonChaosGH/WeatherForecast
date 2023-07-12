package com.example.weatherforecast.ui.weather

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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.weatherforecast.R
import com.example.weatherforecast.collectEventFlow
import com.example.weatherforecast.collectFlow
import com.example.weatherforecast.databinding.FragmentWeatherBinding
import com.example.weatherforecast.entity.AirState
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val binding by viewBinding(FragmentWeatherBinding::class.java)

    private val viewModel by viewModels<WeatherViewModel>()

    private val adapter = ForecastAdapter()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private val requestLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotLocationPermissionResult
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        refreshLayout.setColorSchemeResources(R.color.main_text_color)
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.main_color)
        refreshLayout.setOnRefreshListener { viewModel.refreshWeather() }

        favoriteImageView.setOnClickListener { viewModel.addOrRemoveFromFavorite() }
        searchByCoordinatesImageView.setOnClickListener { getWeatherByCoordinates() }

        observeEditorActionListener()
        observeWeatherState()
        observeEvents()
    }

    private fun observeEditorActionListener() {
        binding.cityEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try {
                    if (binding.cityEditText.text?.isBlank() == true) {
                        viewModel.setEmptyFieldException(true)
                    } else {
                        viewModel.setEmptyFieldException(false)
                        viewModel.getWeatherByCity(
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

    private fun observeWeatherState() = with(binding) {
        collectFlow(viewModel.state) { state ->
            cityNameTextView.text = state.city
            temperatureTextView.text = state.temperature
            currentWeatherTextView.text = state.description
            currentDateTextView.text = state.data
            feelsLikeTextView.text = state.feelsLike
            humidityTextView.text = state.humidity
            pressureTextView.text = state.pressure
            windSpeedTextView.text = state.windSpeed
            progressBar.isVisible = state.isLoading
            cityTextInput.isEnabled = !state.isLoading
            searchByCoordinatesImageView.isEnabled = !state.isLoading
            weatherIconImageView.setImageResource(state.icon.iconResId)
            refreshLayout.isRefreshing = state.isRefreshing
            favoriteImageView.setFavorites(state.isFavorites)
            cityEditText.error =
                if (state.emptyCityError) getString(R.string.error_field_is_empty) else null
            adapter.items = state.forecastState
            setAirState(state.airState)
        }
    }

    private fun ImageView.setFavorites(isFavorites: Boolean) {
        val icon = when (isFavorites) {
            true -> R.drawable.ic_baseline_favorite_24
            false -> R.drawable.ic_baseline_favorite_border_24
        }
        setImageResource(icon)
    }

    @SuppressLint("ResourceType")
    private fun setAirState(airState: AirState) = with(airState) {
        with(binding) {
            valueNO2.text = no2
            qualityNO2.setQuality(no2Quality.qualityResId, no2Quality.colorResId)
            valueO3.text = o3
            qualityO3.setQuality(o3Quality.qualityResId, o3Quality.colorResId)
            valuePM10.text = pm10
            qualityPM10.setQuality(pm10Quality.qualityResId, pm10Quality.colorResId)
            valuePM25.text = pm25
            qualityPM25.setQuality(pm25Quality.qualityResId, pm25Quality.colorResId)
        }
    }

    private fun TextView.setQuality(qualityResId: Int, colorResId: Int) {
        this.text = getString(qualityResId)
        this.setTextColor(
            ContextCompat.getColor(requireContext(), colorResId)
        )
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
                    viewModel.getWeatherByCoordinates(coordinates)
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