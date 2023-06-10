package com.example.domain.weather

import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun loadingWeatherByCity(city: City): ResponseResult<WeatherEntity>

    suspend fun loadingWeatherByCoordinates(coordinates: Coordinates): ResponseResult<WeatherEntity>
}