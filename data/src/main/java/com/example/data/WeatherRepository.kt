package com.example.data

import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.data.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherFlow(): Flow<WeatherEntity?>

    suspend fun loadWeatherByCoordinates(coordinates: Coordinates)

    suspend fun loadWeatherByCity(city: City)
}