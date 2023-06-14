package com.example.weatherforecast.ui.mainweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.weather.ListenMainWeatherUseCase
import com.example.domain.weather.LoadMainWeatherByCityUseCase
import com.example.domain.weather.LoadMainWeatherByCoordinatesUseCase
import com.example.weatherforecast.R
import com.example.weatherforecast.SideEffect
import com.example.weatherforecast.entity.MainWeatherState
import com.example.weatherforecast.mappers.toMainWeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val loadMainWeatherByCityUseCase: LoadMainWeatherByCityUseCase,
    private val loadMainWeatherByCoordinatesUseCase: LoadMainWeatherByCoordinatesUseCase,
    private val listenMainWeatherUseCase: ListenMainWeatherUseCase
) : ViewModel() {

    private val _showMessageResEvent = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    val showMessageResEvent = _showMessageResEvent.asStateFlow()

    private val _state = MutableStateFlow<MainWeatherState?>(null)
    val state: StateFlow<MainWeatherState?> = _state.asStateFlow()

    init {
        listenMainWeather()
    }

    private fun listenMainWeather() {

        listenMainWeatherUseCase.listenMainWeather().onEach { mainWeather ->
            mainWeather?.toMainWeatherState()?.let {
                _state.value = it
            }
        }
            .launchIn(viewModelScope)
    }

    fun showMessage(messageRes: Int) {
        _showMessageResEvent.value = SideEffect(messageRes)
    }

    fun getMainWeatherByCity(city: City) {
        viewModelScope.launch {
            try {
                loadMainWeatherByCityUseCase.loadingMainWeatherByCity(city)
            } catch (ex: Exception) {
                showMessage(R.string.error)
            }
        }
    }

    fun getMainWeatherByCoordinates(coordinates: Coordinates) {
        viewModelScope.launch {
            try {
                loadMainWeatherByCoordinatesUseCase.loadingMainWeatherByCity(coordinates)
            } catch (ex: Exception) {
                showMessage(R.string.error)
            }
        }
    }
//        getWeatherByCityUseCase.getWeatherByCity(city)
//            .onEach {
//                _state.value = it
//            }
//            .launchIn(viewModelScope)

}
