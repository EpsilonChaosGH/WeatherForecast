package com.example.weatherforecast.mappers

import com.example.domain.entity.MainWeatherEntity
import com.example.weatherforecast.FORMAT_EEE_HH_mm
import com.example.weatherforecast.entity.MainWeatherState

fun MainWeatherEntity.toMainWeatherState() = MainWeatherState(

    isFavorites = isFavorites,
    weatherState = weatherEntity.toWeatherState(),
    airState = airEntity.toAirState(),
    forecastState = forecastEntityList.map {
        it.toForecastState(
            FORMAT_EEE_HH_mm,
            weatherEntity.timezone
        )
    }
)