package com.example.data.entity

import androidx.room.*

@Entity(tableName = "weather")
data class MainWeatherDbEntity(
    @PrimaryKey @ColumnInfo(name = "weather_city") val weatherCity: String,
    @ColumnInfo(name = "is_current") var isCurrent: Boolean,
    @ColumnInfo(name = "is_favorites") var isFavorites: Boolean,
    @Embedded val weather: WeatherDbEntity,
    @Embedded val air: AirDbEntity
)