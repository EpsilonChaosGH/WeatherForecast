package com.example.weatherforecast.mappers

import com.example.data.entity.FavoritesEntity
import com.example.data.entity.SettingsState
import com.example.weatherforecast.utils.FORMAT_HH_mm
import com.example.weatherforecast.model.FavoritesItem
import com.example.weatherforecast.model.WeatherType
import com.example.weatherforecast.utils.format
import com.example.weatherforecast.model.Units
import kotlin.math.roundToInt

internal fun FavoritesEntity.toFavoritesItem(
    settingsState: SettingsState
) = FavoritesItem(
    cityId = cityId,
    city = city,
    country = country,
    temperature = "${temperature.roundToInt()}${Units.getUnits(settingsState.selectedUnits)}",
    weatherType = WeatherType.find(icon),
    description = description,
    feelsLike = "${feelsLike.roundToInt()}°",
    humidity = "$humidity %",
    pressure = "$pressure hPa",
    windSpeed = "${windSpeed.roundToInt()} m/s",
    data = data.format(FORMAT_HH_mm, timezone),
    coordinates = coordinates
)