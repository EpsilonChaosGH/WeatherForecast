package com.example.data.mappers

import com.example.data.dbentity.ForecastDbEntity
import com.example.data.entity.ForecastResponse
import com.example.domain.entity.ForecastEntity

internal fun ForecastResponse.toForecastList(): List<ForecastEntity> {
    val forecastList = mutableListOf<ForecastEntity>()
    list.map {
        forecastList.add(
            ForecastEntity(
                city_id = city.id,
                city = city.name,
                temperature = it.main.temp,
                icon = "ic_${it.weather.firstOrNull()?.icon}",
                data = (it.dt + city.timezone) * 1000,
            )
        )
    }
    return forecastList
}

internal fun ForecastEntity.toForecastDbEntity() = ForecastDbEntity(
    id = 0,
    city_id = city_id,
    city = city,
    temperature = temperature,
    icon = icon,
    data = data
)

internal fun ForecastDbEntity.toForecastEntity() = ForecastEntity(
    city_id = city_id,
    city = city,
    temperature = temperature,
    icon = icon,
    data = data
)