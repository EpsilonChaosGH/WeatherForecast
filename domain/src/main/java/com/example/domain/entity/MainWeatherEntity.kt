package com.example.domain.entity

data class MainWeatherEntity(
    val id: Long,
    val isFavorites: Boolean,
    val weatherEntity: WeatherEntity,
    val airEntity: AirEntity,
    val forecastEntityList: List<ForecastEntity>,
)