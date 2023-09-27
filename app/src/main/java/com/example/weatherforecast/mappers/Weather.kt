package com.example.weatherforecast.mappers

import com.example.data.entity.Coordinates
import com.example.data.entity.SettingsState
import com.example.data.entity.WeatherEntity
import com.example.data.source.local.entity.WeatherDbEntity
import com.example.weatherforecast.FORMAT_EEE_HH_mm
import com.example.weatherforecast.FORMAT_EEE_d_MMMM_HH_mm
import com.example.weatherforecast.SideEffect
import com.example.weatherforecast.model.WeatherState
import com.example.weatherforecast.model.WeatherType
import com.example.weatherforecast.format
import kotlin.math.roundToInt


internal fun WeatherEntity.toWeatherState(
    settingsState: SettingsState,
    userMessage: SideEffect<Int?>,
    isLoading: Boolean,
    emptyCityError: Boolean
): WeatherState = WeatherState(
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
    airState = air.toAirState(),
    settingsState = settingsState,
    userMessage = userMessage,
    isLoading = isLoading,
    emptyCityError = emptyCityError
)