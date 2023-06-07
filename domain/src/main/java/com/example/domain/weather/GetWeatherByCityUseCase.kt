package com.example.domain.weather

import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

class GetWeatherByCityUseCase(
    private val weatherRepository: WeatherRepository
) {
    fun getWeatherByCity(city: City): Flow<ResponseResult<WeatherEntity>> {
        return weatherRepository.loadingWeatherByCity(city)
    }
}