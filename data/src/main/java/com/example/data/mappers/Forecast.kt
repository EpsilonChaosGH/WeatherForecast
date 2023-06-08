package com.example.data.mappers

import com.example.data.entity.ForecastResponse
import com.example.domain.entity.ForecastEntity

fun ForecastResponse.toForecastList(): List<ForecastEntity> {
    val forecastList = mutableListOf<ForecastEntity>()
    list.map {
        forecastList.add(
            ForecastEntity(
                id = city.id,
                city = city.name,
                temperature = it.main.temp,
                icon = "ic_${it.weather.firstOrNull()?.icon}",
                data = (it.dt + city.timezone) * 1000,
            )
        )
    }
    return forecastList
}