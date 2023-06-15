package com.example.data.mappers

import com.example.data.entity.ForecastEntity
import com.example.data.entity.response.ForecastResponse

internal fun ForecastResponse.toForecastList(): List<ForecastEntity> {
    val forecastList = mutableListOf<ForecastEntity>()
    list.map {
        forecastList.add(
            ForecastEntity(
                temperature = it.main.temp,
                icon = "ic_${it.weather.firstOrNull()?.icon}",
                data = (it.dt + city.timezone) * 1000,
            )
        )
    }
    return forecastList
}