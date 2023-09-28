package com.example.weatherforecast.model

data class ForecastState(
    val temperature: String,
    val data: String,
    val weatherType: WeatherType,
)