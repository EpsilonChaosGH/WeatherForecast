package com.example.weatherforecast.model

data class FavoritesItem(
    val cityId: Long,
    val city: String,
    val country: String,
    val temperature: String,
    val weatherType: WeatherType,
    val description: String,
    val feelsLike: String,
    val humidity: String,
    val pressure: String,
    val windSpeed: String,
    val data: String,
)