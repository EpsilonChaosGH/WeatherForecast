package com.example.weatherforecast.mappers

import com.example.data.entity.FavoritesEntity
import com.example.weatherforecast.FORMAT_EEE_d_MMMM_HH_mm
import com.example.weatherforecast.entity.FavoritesItem
import com.example.weatherforecast.entity.FavoritesState
import com.example.weatherforecast.entity.WeatherType
import com.example.weatherforecast.format
import kotlin.math.roundToInt


internal fun List<FavoritesEntity>.toFavoritesState() =
    FavoritesState(this.map { it.toFavoritesItem() })

internal fun FavoritesEntity.toFavoritesItem() = FavoritesItem(
    cityId = cityId,
    city = city,
    country = country,
    temperature = "${temperature.roundToInt()}°C",
    weatherType = WeatherType.find(icon),
    description = description,
    feelsLike = "${feelsLike.roundToInt()}°",
    humidity = "$humidity %",
    pressure = "$pressure hPa",
    windSpeed = "${windSpeed.roundToInt()} m/s",
    data = data.format(FORMAT_EEE_d_MMMM_HH_mm, timezone)
)