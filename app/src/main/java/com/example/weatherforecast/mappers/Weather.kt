package com.example.weatherforecast.mappers

import com.example.data.entity.Coordinates
import com.example.data.entity.dbentity.WeatherDbEntity
import com.example.weatherforecast.FORMAT_EEE_HH_mm
import com.example.weatherforecast.FORMAT_EEE_d_MMMM_HH_mm
import com.example.weatherforecast.entity.WeatherState
import com.example.weatherforecast.entity.WeatherType
import com.example.weatherforecast.format
import kotlin.math.roundToInt


internal fun WeatherDbEntity.toWeatherState(): WeatherState = WeatherState(
    isFavorites = isFavorites,
    id = id,
    coordinates = Coordinates(
        lat = lat,
        lon = lon
    ),
    city = city,
    country = country,
    temperature = "${temperature.roundToInt()}°C",
    icon = WeatherType.find(icon),
    description = description,
    feelsLike = "${feelsLike.roundToInt()}°",
    humidity = "$humidity %",
    pressure = "$pressure hPa",
    windSpeed = "${windSpeed.roundToInt()} m/s",
    data = data.format(FORMAT_EEE_d_MMMM_HH_mm, timezone),
    timezone = timezone,
    forecastState = forecast.toForecastState(FORMAT_EEE_HH_mm, timezone),
    airState = air.toAirState()
)