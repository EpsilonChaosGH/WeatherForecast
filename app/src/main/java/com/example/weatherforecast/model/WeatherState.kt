package com.example.weatherforecast.model

import com.example.data.entity.Coordinates
import com.example.data.entity.SettingsState

data class WeatherState(
    val isFavorites: Boolean,
    val id: Long,
    val coordinates: Coordinates,
    val city: String,
    val country: String,
    val temperature: String,
    val icon: WeatherType,
    val description: String,
    val feelsLike: String,
    val humidity: String,
    val pressure: String,
    val windSpeed: String,
    val data: String,
    val timezone: Long,
    val forecastState: List<ForecastState>,
    val airState: AirState,
    val userMessage: SideEffect<Int?> = SideEffect(null),
    val isLoading: Boolean = false,
    val emptyCityError: Boolean = false,
)