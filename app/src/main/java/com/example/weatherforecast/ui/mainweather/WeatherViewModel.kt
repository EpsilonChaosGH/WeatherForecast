package com.example.weatherforecast.ui.mainweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.WeatherEntity
import com.example.domain.weather.GetWeatherByCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ResponseResult<WeatherEntity>?>(null)
    val state: StateFlow<ResponseResult<WeatherEntity>?> = _state.asStateFlow()

    init {
        getWeatherByCity(City("tver"))
    }

    fun getWeatherByCity(city: City) {
        getWeatherByCityUseCase.getWeatherByCity(city)
            .onEach {
                _state.value = it
            }
            .launchIn(viewModelScope)
    }
}
