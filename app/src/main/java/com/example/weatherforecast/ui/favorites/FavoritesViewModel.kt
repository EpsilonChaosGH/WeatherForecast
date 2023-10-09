package com.example.weatherforecast.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FavoritesRepository
import com.example.data.SettingsRepository
import com.example.data.WeatherRepository
import com.example.data.entity.City
import com.example.weatherforecast.WhileUiSubscribed
import com.example.weatherforecast.model.FavoritesState
import com.example.weatherforecast.mappers.toFavoritesItem
import com.example.weatherforecast.mappers.toFavoritesState
import com.example.weatherforecast.model.SideEffect
import com.example.weatherforecast.model.SupportedLanguage
import com.example.weatherforecast.model.Units
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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
    private val _favoritesRepository = favoritesRepository.getFavoritesFlow()


    val uiState: StateFlow<FavoritesState?> = combine(
        _favoritesRepository,
        _userMessage,
        _isLoading,
        _emptyList
    ) { favorites, userMessage, isLoading, emptyList ->
        favorites?.let {
            FavoritesState(
                it.map { it.toFavoritesItem() },
                userMessage,
                isLoading,
                emptyList
            )
        }
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    fun refreshFavorites() {
        viewModelScope.launch {
            setLoading(true)
            val favorites = favoritesRepository.refreshFavorites().map { it.toFavoritesItem() }
            uiState.value?.favorites.let {
                _favoritesRepository
            }
           // setState { copy(favorites = favorites) }
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
        viewModelScope.launch {
            weatherRepository.loadWeatherByCity(city = city)
        }
    }

    private fun listenFavorites() {
        viewModelScope.launch {
            favoritesRepository.getFavoritesFlow().collect { favoritesList ->
                if (!favoritesList.isNullOrEmpty()) {
                  //  setState { favoritesList.toFavoritesState() }
                } else {
                   // setState { copy(emptyListState = true) }
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}