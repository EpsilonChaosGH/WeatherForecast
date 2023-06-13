package com.example.data.mappers

import com.example.data.Const
import com.example.data.dbentity.MainWeatherDbEntity
import com.example.domain.entity.MainWeatherEntity

internal fun MainWeatherEntity.toWeatherAndAirDbEntity() = MainWeatherDbEntity(
    mainWeatherKey = Const.MAIN_WEATHER_KEY,
    id = id,
    isFavorites = isFavorites,
    weather = weatherEntity.toWeatherDbEntity(),
    air = airEntity.toAirDbEntity(),
    forecast = forecastEntityList
)

internal fun MainWeatherDbEntity.toMainWeatherEntity() = MainWeatherEntity(
    id = id,
    isFavorites = false,
    weatherEntity = weather.toWeatherEntity(id),
    airEntity = air.toAirEntity(),
    forecastEntityList = forecast
)