package com.example.weatherforecast.ui.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FavoritesRepository
import com.example.data.WeatherRepository
import com.example.data.entity.City
import com.example.data.utils.CityNotFoundException
import com.example.data.utils.ConnectionException
import com.example.data.utils.InvalidApiKeyException
import com.example.data.utils.RequestRateLimitException
import com.example.weatherforecast.R
import com.example.weatherforecast.WhileUiSubscribed
import com.example.weatherforecast.model.FavoritesState
import com.example.weatherforecast.mappers.toFavoritesItem
import com.example.weatherforecast.model.SideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)
    private val _emptyList = MutableStateFlow(false)

    val uiState: StateFlow<FavoritesState?> = combine(
        favoritesRepository.favoritesFlow,
        _userMessage,
        _isLoading,
        _emptyList
    ) { favorites, userMessage, isLoading, emptyList ->
            Log.e("aaa","fav VM")
            FavoritesState(
                favorites.map { it.toFavoritesItem() },
                userMessage,
                isLoading,
                emptyList
            )
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        val result = when (exception) {
            is ConnectionException -> R.string.error_connection
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
            favoritesRepository.getFavoritesFlow()
        }
    }

    private fun showMessage(messageRes: Int) {
        _userMessage.value = SideEffect(messageRes)
    }

    fun refreshFavorites() {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            favoritesRepository.refreshFavorites()
            setLoading(false)
        }
    }

    fun deleteFromFavorites(id: Int) {
        viewModelScope.launch {
            uiState.value?.let {
                favoritesRepository.deleteFromFavoritesById(it.favorites[id].cityId)
            }
        }
    }

    fun loadWeatherByCity(city: City) {
        Log.e("aaa",uiState.value?.emptyList.toString())
        viewModelScope.launch(exceptionHandler) {
            weatherRepository.loadWeatherByCity(city = city)
        }
        Log.e("aaa",uiState.value?.emptyList.toString())
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}