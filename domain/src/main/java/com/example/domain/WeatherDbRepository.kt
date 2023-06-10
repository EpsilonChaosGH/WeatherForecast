package com.example.domain

import com.example.domain.entity.ForecastEntity
import com.example.domain.entity.MainWeatherEntity
import com.example.domain.entity.WeatherAndAirEntity
import kotlinx.coroutines.flow.Flow

interface WeatherDbRepository {

    fun getMainWeatherFlow(): Flow<MainWeatherEntity?>

    suspend fun insertMainWeather(weather: WeatherAndAirEntity)

    suspend fun insertForecast(forecast: List<ForecastEntity>)
}