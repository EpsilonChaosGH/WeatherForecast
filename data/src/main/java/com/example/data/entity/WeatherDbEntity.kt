package com.example.data.entity

import androidx.room.ColumnInfo


data class WeatherDbEntity(
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "feels_like") val feelsLike: Double,
    @ColumnInfo(name = "humidity") val humidity: Long,
    @ColumnInfo(name = "pressure") val pressure: Long,
    @ColumnInfo(name = "wind_speed") val windSpeed: Double,
    @ColumnInfo(name = "data") val data: Long,
    @ColumnInfo(name = "timezone") val timezone: Long,
    @ColumnInfo(name = "longitude") val lon: String,
    @ColumnInfo(name = "latitude") val lat: String,
)