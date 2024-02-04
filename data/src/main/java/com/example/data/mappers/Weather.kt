package com.example.data.mappers

import com.example.data.utils.Const
import com.example.data.entity.Coordinates
import com.example.data.entity.FavoritesEntity
import com.example.data.source.network.response.CurrentWeatherResponse
import com.example.data.entity.ForecastEntity
import com.example.data.entity.SettingsState
import com.example.data.entity.WeatherEntity
import com.example.data.source.local.entity.AirDbEntity
import com.example.data.source.local.entity.WeatherDbEntity

internal fun CurrentWeatherResponse.toWeatherDbEntity(
    isFavorites: Boolean,
    forecast: List<ForecastEntity>,
    air: AirDbEntity,
): WeatherDbEntity = WeatherDbEntity(
    weatherKey = Const.WEATHER_KEY,
    isFavorites = isFavorites,
    id = id,
    lon = coord.lon.toString(),
    lat = coord.lat.toString(),
    city = name,
    country = sys.country,
    temperature = main.temp,
    icon = "ic_${weather.firstOrNull()?.icon}",
    description = weather.firstOrNull()?.description ?: "unknown",
    feelsLike = main.feelsLike,
    humidity = main.humidity,
    pressure = main.pressure,
    windSpeed = wind.speed,
    data = dt,
    timezone = timezone,
    forecast = forecast,
    air = air
)

internal fun CurrentWeatherResponse.toFavoritesEntity(
    settingsState: SettingsState
) = FavoritesEntity(
    cityId = id,
    city = name,
    country = sys.country,
    temperature = main.temp,
    icon = "ic_${weather.firstOrNull()?.icon}",
    description = weather.firstOrNull()?.description ?: "unknown",
    feelsLike = main.feelsLike,
    humidity = main.humidity,
    pressure = main.pressure,
    windSpeed = wind.speed,
    data = dt,
    timezone = timezone,
    coordinates = Coordinates(
        lat = coord.lat.toString(),
        lon = coord.lon.toString()
    ),
    settingsState = settingsState
)

internal fun WeatherDbEntity.toWeatherEntity(
    settingsState: SettingsState
) = WeatherEntity(
    isFavorites = isFavorites,
    id = id,
    lon = lon,
    lat = lat,
    city = city,
    country = country,
    temperature = temperature,
    icon = icon,
    description = description,
    feelsLike = feelsLike,
    humidity = humidity,
    pressure = pressure,
    windSpeed = windSpeed,
    data = data,
    timezone = timezone,
    forecast = forecast,
    air = air,
    settingsState = settingsState
)