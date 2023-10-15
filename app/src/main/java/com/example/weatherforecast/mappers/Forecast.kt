package com.example.weatherforecast.mappers

import com.example.data.entity.ForecastEntity
import com.example.weatherforecast.model.ForecastState
import com.example.weatherforecast.model.WeatherType
import com.example.weatherforecast.format
import kotlin.math.roundToInt

fun List<ForecastEntity>.toForecastState(dataFormat: String, timeZone: Long): List<ForecastState> {
    return this.map {
        ForecastState(
            temperature = "${it.temperature.roundToInt()}°C",
            data = it.data.format(dataFormat, timeZone),
            weatherType = WeatherType.find(it.icon),
        )
    }
}