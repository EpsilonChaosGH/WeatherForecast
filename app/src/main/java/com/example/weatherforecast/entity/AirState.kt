package com.example.weatherforecast.entity


data class AirState(
    val no2: String,
    val no2Quality: AirQuality,
    val o3: String,
    val o3Quality: AirQuality,
    val pm10: String,
    val pm10Quality: AirQuality,
    val pm25: String,
    val pm25Quality: AirQuality
)
