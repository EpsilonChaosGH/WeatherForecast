package com.example.weatherforecast.model

data class FavoritesState(
    val favorites: List<FavoritesItem> = listOf(),
    val isRefreshing: Boolean = false,
    val emptyListState: Boolean = false,
)