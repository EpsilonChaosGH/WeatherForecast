package com.example.data.source.local

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class UpdateFavoritesTuple(
    @ColumnInfo(name = "weather_key") @PrimaryKey val weatherKey: String,
    @ColumnInfo(name = "is_favorites") var isFavorites: Boolean,
)

//data class UpdateCurrentTuple(
//    @ColumnInfo(name = "weather_city") @PrimaryKey val city: String,
//    @ColumnInfo(name = "is_current") var isCurrent: Boolean,
//)
//
//data class CityTuple(
//    @ColumnInfo(name = "weather_city") @PrimaryKey val city: String,
//)
//
//data class UpdateMainWeatherTuple(
//    @PrimaryKey @ColumnInfo(name = "weather_city") val weatherCity: String,
//    @Embedded val weather: WeatherDbEntity,
//    @Embedded val air: AirDbEntity
//)