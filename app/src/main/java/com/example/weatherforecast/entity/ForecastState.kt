package com.example.weatherforecast.entity


data class ForecastState(
    val temperature: String,
    val data: String,
    val weatherType: WeatherType,
)