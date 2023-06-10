package com.example.data.mappers

import com.example.data.dbentity.WeatherDbEntity
import com.example.data.entity.WeatherResponse
import com.example.domain.entity.WeatherEntity


internal fun WeatherResponse.toWeatherEntity(): WeatherEntity = WeatherEntity(
    id = id,
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
    lon = coord.lon.toString(),
    lat = coord.lat.toString(),
)

internal fun WeatherEntity.toWeatherDbEntity() = WeatherDbEntity(
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
    lon = lon,
    lat = lat
)

internal fun WeatherDbEntity.toWeatherEntity(
    id: Long
) = WeatherEntity(
    id = id,
    city =city,
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
    lon = lon,
    lat = lat
)