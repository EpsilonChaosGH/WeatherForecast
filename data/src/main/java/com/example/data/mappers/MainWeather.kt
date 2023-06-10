package com.example.data.mappers

import android.util.Log
import com.example.data.dbentity.WeatherAndAirDbEntity
import com.example.data.dbentity.MainWeatherDbEntity
import com.example.domain.entity.MainWeatherEntity
import com.example.domain.entity.WeatherAndAirEntity

internal fun WeatherAndAirEntity.toWeatherAndAirDbEntity() = WeatherAndAirDbEntity(
    id = id,
    isFavorites = isFavorites,
    weather = weatherEntity.toWeatherDbEntity(),
    air = airEntity.toAirDbEntity()
)

internal fun MainWeatherDbEntity.toMainWeatherEntity() = MainWeatherEntity(
    id = weather.id,
    isFavorites = false,
    weatherEntity = weather.weather.toWeatherEntity(weather.id),
    airEntity = weather.air.toAirEntity(),
    forecastEntityList = forecastList.map { it.toForecastEntity() }
)