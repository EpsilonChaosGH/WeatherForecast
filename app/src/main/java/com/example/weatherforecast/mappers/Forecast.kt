package com.example.weatherforecast.mappers

import com.example.domain.entity.ForecastEntity
import com.example.weatherforecast.entity.ForecastState
import com.example.weatherforecast.entity.WeatherType
import com.example.weatherforecast.format
import kotlin.math.roundToInt


fun ForecastEntity.toForecastState(dataFormat: String, timeZone: Long) = ForecastState(
    temperature = "${temperature.roundToInt()}°C",
    data = data.format(dataFormat, timeZone),
    weatherType = WeatherType.find(icon),
)