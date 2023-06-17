package com.example.weatherforecast.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FavoritesRepository
import com.example.weatherforecast.entity.FavoritesState
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
    private val favoritesRepository: FavoritesRepository
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
                FavoritesState(favoritesRepository.refreshFavorites().map { it.toFavoritesItem() })
        }
    }

    fun deleteFromFavorites(id: Long) {
        viewModelScope.launch {
            favoritesRepository.deleteFromFavoritesById(id)
        }
    }

    fun showDetails(id: Long) {

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