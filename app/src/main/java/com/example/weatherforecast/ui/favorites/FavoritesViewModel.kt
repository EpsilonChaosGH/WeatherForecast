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
import com.example.weatherforecast.model.SupportedLanguage
import com.example.weatherforecast.model.Units
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository

) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        listenFavorites()
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            setState { copy(isRefreshing = true) }
            val settings = settingsRepository.getSettingsFlow().first()
            val favorites = favoritesRepository.refreshFavorites(
                units = Units.values()[settings.selectedUnitIndex].value,
                language = SupportedLanguage.values()[settings.selectedLanguageIndex].languageValue
            ).map { it.toFavoritesItem() }

            setState { copy(favorites = favorites) }
            setState { copy(isRefreshing = false) }
        }
    }

    fun deleteFromFavorites(id: Long) {
        viewModelScope.launch {
            favoritesRepository.deleteFromFavoritesById(id)
        }
    }

    fun loadWeatherByCity(city: City) {
        viewModelScope.launch {
            val settings = settingsRepository.getSettingsFlow().first()
            weatherRepository.loadWeatherByCity(
                city = city,
                units = Units.values()[settings.selectedUnitIndex].value,
                language = SupportedLanguage.values()[settings.selectedLanguageIndex].languageValue
            )
        }
    }

    private fun listenFavorites() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettingsFlow().first()
            favoritesRepository.getFavoritesFlow(
                units = Units.values()[settings.selectedUnitIndex].value,
                language = SupportedLanguage.values()[settings.selectedLanguageIndex].languageValue
            ).collect { favoritesList ->
                if (!favoritesList.isNullOrEmpty()) {
                    setState { favoritesList.toFavoritesState() }
                } else {
                    setState { copy(emptyListState = true) }
                }
            }
        }
    }

    private suspend fun setState(stateReducer: FavoritesState.() -> FavoritesState) {
        _state.emit(stateReducer(state.value))
    }
}