package com.example.weatherforecast.entity


data class FavoritesState(
    val favorites: List<FavoritesItem>,
    val emptyListState: Boolean = false,
    val isRefreshing: Boolean = false,
)
