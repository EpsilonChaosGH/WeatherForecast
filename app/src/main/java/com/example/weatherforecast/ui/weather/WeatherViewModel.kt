package com.example.weatherforecast.ui.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.CityNotFoundException
import com.example.data.utils.ConnectionException
import com.example.data.FavoritesRepository
import com.example.data.utils.InvalidApiKeyException
import com.example.data.utils.RequestRateLimitException
import com.example.data.WeatherRepository
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.data.source.local.entity.FavoritesDbEntity
import com.example.weatherforecast.R
import com.example.weatherforecast.model.WeatherState
import com.example.weatherforecast.mappers.toWeatherState
import com.example.weatherforecast.model.SideEffect
import com.example.weatherforecast.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)
    private val _emptyCityError = MutableStateFlow(false)

    val uiState: StateFlow<WeatherState?> = combine(
        weatherRepository.observeWeather(),
        _userMessage,
        _isLoading,
        _emptyCityError
    ) { weather, userMessage, isLoading, emptyCityError ->
        Log.e("aaaAAAAA", "${weather?.city} + ${weather?.lat} + ${weather?.lon} + $isLoading + $emptyCityError ")
        weather?.toWeatherState(
            userMessage,
            isLoading,
            emptyCityError
        )?.let { return@let it }
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("aaa111",exception.message.toString())
        Log.e("aaa222",exception.toString())
        val result = when (exception) {
            is IOException -> R.string.error_connection
            is CityNotFoundException -> R.string.error_404_city_not_found
            is InvalidApiKeyException -> R.string.error_401_invalid_api_key
            is RequestRateLimitException -> R.string.error_429_request_rate_limit_surpassing
            else -> R.string.error_internal
        }
        showMessage(result)
        setLoading(false)
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            weatherRepository.listenWeather()
        }
    }

    fun refreshWeather() {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            uiState.value?.let { state ->
                weatherRepository.loadWeatherByCoordinates(
                    Coordinates(
                        lat = state.coordinates.lat,
                        lon = state.coordinates.lon,
                    )
                )
            }
            setLoading(false)
        }
    }

    fun getWeatherByCity(city: City) {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            weatherRepository.loadWeatherByCity(
                city = city
            )
            setLoading(false)
        }
    }

    fun getWeatherByCoordinates(coordinates: Coordinates) {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            weatherRepository.loadWeatherByCoordinates(
                coordinates = coordinates
            )
            setLoading(false)
        }
    }

    fun addOrRemoveFromFavorite() {
        viewModelScope.launch(exceptionHandler) {
            uiState.value?.let { value ->
                if (value.isFavorites) {
                    favoritesRepository.deleteFromFavoritesById(value.id)
                } else {
                    favoritesRepository.addToFavorites(
                        FavoritesDbEntity(
                            cityId = value.id,
                            city = value.city,
                            lon = value.coordinates.lon,
                            lat = value.coordinates.lat
                        )
                    )
                }
            }
        }
    }

    fun showMessage(messageRes: Int) {
        _userMessage.value = SideEffect(messageRes)
    }

    fun setEmptyFieldException(emptyCityError: Boolean) {
        _emptyCityError.value = emptyCityError
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}