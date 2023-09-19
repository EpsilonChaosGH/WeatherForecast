package com.example.weatherforecast.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FavoritesRepository
import com.example.data.SettingsRepository
import com.example.data.WeatherRepository
import com.example.data.entity.City
import com.example.weatherforecast.model.FavoritesState
import com.example.weatherforecast.mappers.toFavoritesItem
import com.example.weatherforecast.mappers.toFavoritesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository

) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesState?>(null)
    val state: StateFlow<FavoritesState?> = _state.asStateFlow()

    init {
        listenFavorites()
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isRefreshing = true)
            _state.value =
                FavoritesState(favoritesRepository.refreshFavorites(
                    units = settingsRepository.getUnits(),
                    language = settingsRepository.getLanguage()
                ).map { it.toFavoritesItem() })
        }
    }

    fun deleteFromFavorites(id: Long) {
        viewModelScope.launch {
            favoritesRepository.deleteFromFavoritesById(id)
        }
    }

    fun loadWeatherByCity(city: City) {
        viewModelScope.launch {
            weatherRepository.loadWeatherByCity(
                city = city,
                units = settingsRepository.getUnits(),
                language = settingsRepository.getLanguage()
            )
        }
    }

    private fun listenFavorites() {
        viewModelScope.launch {
            favoritesRepository.getFavoritesFlow().collect { favoritesList ->
                if (!favoritesList.isNullOrEmpty()) {
                    _state.value = favoritesList.toFavoritesState()
                } else {
                    _state.value = _state.value?.copy(emptyListState = true)
                }
            }
        }
    }
}