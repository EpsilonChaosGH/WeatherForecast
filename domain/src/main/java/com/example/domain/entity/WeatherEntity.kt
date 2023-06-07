package com.example.domain.entity


data class WeatherEntity(
    val id: Long,
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
    val lon: String,
    val lat: String,
)