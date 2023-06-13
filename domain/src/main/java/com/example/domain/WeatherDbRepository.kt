package com.example.domain

import com.example.domain.entity.MainWeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherDbRepository {

    fun getMainWeatherByIdFlow(): Flow<MainWeatherEntity?>

    suspend fun insertMainWeather(weather: MainWeatherEntity)

}