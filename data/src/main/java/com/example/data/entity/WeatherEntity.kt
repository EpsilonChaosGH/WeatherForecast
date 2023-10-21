package com.example.data.entity

import com.example.data.source.local.entity.AirDbEntity

data class WeatherEntity(
    val isFavorites: Boolean,
    val id: Long,
    val lon: String,
    val lat: String,
    val city: String,
    val country: String,
    val temperature: Double,
    val icon: String,
    val description: String,
    val feelsLike: Double,
    val humidity: Long,
    val pressure: Long,
    val windSpeed: Double,
    val data: Long,
    val timezone: Long,
    val forecast: List<ForecastEntity>,
    val air: AirDbEntity,
    val settingsState: SettingsState
)