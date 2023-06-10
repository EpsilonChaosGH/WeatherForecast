package com.example.domain.weather

import com.example.domain.WeatherDbRepository
import com.example.domain.entity.MainWeatherEntity
import kotlinx.coroutines.flow.Flow

class ListenMainWeatherUseCase(
    private val weatherDbRepository: WeatherDbRepository
) {
    fun listenMainWeather(): Flow<MainWeatherEntity?> {
        return weatherDbRepository.getMainWeatherFlow()
    }
}