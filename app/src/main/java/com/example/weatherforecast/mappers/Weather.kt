package com.example.weatherforecast.mappers

import com.example.domain.entity.WeatherEntity
import com.example.weatherforecast.FORMAT_EEE_HH_mm
import com.example.weatherforecast.format
import com.example.weatherforecast.models.WeatherState
import com.example.weatherforecast.models.WeatherType
import kotlin.math.roundToInt


internal fun WeatherEntity.toWeatherState(): WeatherState = WeatherState(
    id = id,
    city = city,
    country = country,
    temperature = "${temperature.roundToInt()}°C",
    weatherType = WeatherType.find(icon),
    description = description,
    feelsLike = "${feelsLike.roundToInt()}°",
    humidity = "$humidity %",
    pressure = "$pressure hPa",
    windSpeed = "${windSpeed.roundToInt()} m/s",
    data = data.format(FORMAT_EEE_HH_mm, timezone),
    isFavorites = true
)