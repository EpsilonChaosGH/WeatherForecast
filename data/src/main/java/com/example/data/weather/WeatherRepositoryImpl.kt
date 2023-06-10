package com.example.data.weather

import androidx.annotation.WorkerThread
import com.example.data.NotFoundException
import com.example.data.mappers.toWeatherEntity
import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.WeatherEntity
import com.example.domain.executeWithResponse
import com.example.domain.weather.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: WeatherService
) : WeatherRepository {
    @WorkerThread
    override suspend fun loadingWeatherByCity(city: City): ResponseResult<WeatherEntity> {
        return executeWithResponse {
            service.getWeatherByCity(city.city).body()
                ?.toWeatherEntity()
                ?: throw NotFoundException()
        }
    }

    @WorkerThread
    override suspend fun loadingWeatherByCoordinates(coordinates: Coordinates): ResponseResult<WeatherEntity> {
        return executeWithResponse {
            service.getWeatherByCoordinates(
                lat = coordinates.lat,
                lon = coordinates.lon
            ).body()
                ?.toWeatherEntity()
                ?: throw NotFoundException()
        }
    }
}