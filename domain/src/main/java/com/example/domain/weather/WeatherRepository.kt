package com.example.domain.weather

import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun loadingWeatherByCity(city: City): Flow<ResponseResult<WeatherEntity>>

    fun loadingWeatherByCoordinates(coordinates: Coordinates): Flow<ResponseResult<WeatherEntity>>
}