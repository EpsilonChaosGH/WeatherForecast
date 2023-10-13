package com.example.weatherforecast.model


data class FavoritesState(
    val favorites: List<FavoritesItem> = listOf(),
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
    val emptyList: Boolean = false,
)