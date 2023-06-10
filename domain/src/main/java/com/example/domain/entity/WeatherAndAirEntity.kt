package com.example.domain.entity

data class WeatherAndAirEntity(
    val id: Long,
    val isFavorites: Boolean,
    val weatherEntity: WeatherEntity,
    val airEntity: AirEntity,
)