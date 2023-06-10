package com.example.data.dbentity

import androidx.room.Embedded
import androidx.room.Relation

data class MainWeatherDbEntity(
    @Embedded val weather: WeatherAndAirDbEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "city_id"
    )
    val forecastList: List<ForecastDbEntity>
)