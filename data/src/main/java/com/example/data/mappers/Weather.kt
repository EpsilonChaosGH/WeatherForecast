package com.example.data.mappers

import com.example.data.Const
import com.example.data.entity.Coordinates
import com.example.data.entity.FavoritesEntity
import com.example.data.entity.response.CurrentWeatherResponse
import com.example.data.entity.ForecastEntity
import com.example.data.entity.dbentity.AirDbEntity
import com.example.data.entity.dbentity.WeatherDbEntity


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

internal fun CurrentWeatherResponse.toFavoritesEntity() = FavoritesEntity(
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
    )
)