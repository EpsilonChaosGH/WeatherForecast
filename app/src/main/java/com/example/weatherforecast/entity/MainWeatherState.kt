package com.example.weatherforecast.entity


data class MainWeatherState(
    val isFavorites: Boolean,
    val weatherState: WeatherState,
    val airState: AirState,
    val forecastState: List<ForecastState>,
    val emptyCityError: Boolean = false,
)