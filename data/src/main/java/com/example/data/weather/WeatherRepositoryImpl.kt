package com.example.data.weather

import androidx.annotation.WorkerThread
import com.example.data.NotFoundException
import com.example.data.mappers.toWeatherEntity
import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.weather.WeatherRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: WeatherService
) : WeatherRepository {
    @WorkerThread
    override fun loadingWeatherByCity(city: City) = flow {
        try {
            service.getWeatherByCity(city.city).body()?.toWeatherEntity()?.let { entity ->
                emit(ResponseResult.Success(entity))
            } ?: run {
                throw NotFoundException()
            }
        } catch (ex: Exception) {
            emit(ResponseResult.Error(ex))
        }
    }

    @WorkerThread
    override fun loadingWeatherByCoordinates(coordinates: Coordinates) = flow {
        try {
            service.getWeatherByCoordinates(
                lat = coordinates.lat,
                lon = coordinates.lon
            ).body()?.toWeatherEntity()?.let { entity ->
                emit(ResponseResult.Success(entity))
            } ?: run {
                throw NotFoundException()
            }
        } catch (ex: Exception) {
            emit(ResponseResult.Error(ex))
        }
    }

//    executeWithResponse {
//        service.listFollowers(preferences.followersUrl, page)
//            .body()
//            ?.toModelFollowers()
//            ?: emptyList()
//    }
}