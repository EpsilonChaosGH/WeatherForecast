package com.example.data

import com.example.data.entity.dbentity.WeatherDbEntity
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherFlow(): Flow<WeatherDbEntity?>

    suspend fun loadWeatherByCoordinates(coordinates: Coordinates)

    suspend fun loadWeatherByCity(city: City)

}