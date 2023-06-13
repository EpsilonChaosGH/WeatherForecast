package com.example.weatherforecast.ui.mainweather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.City
import com.example.domain.weather.ListenMainWeatherUseCase
import com.example.domain.weather.LoadMainWeatherByCityUseCase
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
    private val listenMainWeatherUseCase: ListenMainWeatherUseCase
) : ViewModel() {

    private val _showMessageResEvent = MutableStateFlow<SideEffect<String?>>(SideEffect(null))
    val showErrorMessageResEvent = _showMessageResEvent.asStateFlow()

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

    fun getWeatherByCity(city: City) {
        viewModelScope.launch {
            try {
                loadMainWeatherByCityUseCase.loadingMainWeatherByCity(city)
            } catch (ex: Exception) {
//                _showMessageResEvent.value = SideEffect(ex.message)
                Log.e("aaaaa catch",ex.message.toString())
                Log.e("aaaaa catch",ex.toString())
                _showMessageResEvent.value = SideEffect("MMMMMMMMMMMMMMMM")
            }
        }

//        getWeatherByCityUseCase.getWeatherByCity(city)
//            .onEach {
//                _state.value = it
//            }
//            .launchIn(viewModelScope)
    }
}
