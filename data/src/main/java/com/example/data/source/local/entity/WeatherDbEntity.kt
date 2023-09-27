package com.example.data.source.local.entity

import androidx.room.*
import com.example.data.entity.ForecastEntity

@Entity(tableName = "weather")
data class WeatherDbEntity(
    @PrimaryKey @ColumnInfo(name = "weather_key") val weatherKey: String,
    @ColumnInfo(name = "is_favorites") var isFavorites: Boolean,
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "longitude") val lon: String,
    @ColumnInfo(name = "latitude") val lat: String,
    @ColumnInfo(name = "city") val city: String,
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
    @ColumnInfo(name = "forecast") val forecast: List<ForecastEntity>,
    @Embedded val air: AirDbEntity
)