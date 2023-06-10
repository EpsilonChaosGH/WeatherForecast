package com.example.weatherforecast.entity

import com.example.weatherforecast.entity.WeatherType


data class ForecastState(
    val temperature: String,
    val data: String,
    val weatherType: WeatherType,
)