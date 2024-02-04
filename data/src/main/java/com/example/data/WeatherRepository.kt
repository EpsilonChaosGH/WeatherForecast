package com.example.data

import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.data.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    @Throws
    fun observeWeather(): Flow<WeatherEntity?>

    @Throws
    suspend fun listenWeather()

    @Throws
    suspend fun loadWeatherByCoordinates(coordinates: Coordinates)

    @Throws
    suspend fun loadWeatherByCity(city: City)
}