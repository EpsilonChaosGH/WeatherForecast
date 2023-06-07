package com.example.data.mappers

import com.example.data.entity.WeatherResponse
import com.example.domain.entity.WeatherEntity


fun WeatherResponse.toWeatherEntity(): WeatherEntity = WeatherEntity(
    id = sys.id,
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