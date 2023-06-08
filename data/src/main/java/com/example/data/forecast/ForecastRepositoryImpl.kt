package com.example.data.forecast

import com.example.data.NotFoundException
import com.example.data.mappers.toForecastList
import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.ForecastEntity
import com.example.domain.forecast.ForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastService: ForecastService
) : ForecastRepository {
    override fun loadingForecastByCity(city: City) = flow {
        try {
            forecastService.getForecastByCity(city.city).body()?.toForecastList()?.let { entity ->
                emit(ResponseResult.Success(entity))
            } ?: run {
                throw NotFoundException()
            }
        } catch (ex: Exception) {
            emit(ResponseResult.Error(ex))
        }
    }

    override fun loadingForecastByCoordinates(coordinates: Coordinates) = flow {
        try {
            forecastService.getForecastByCoordinate(
                lat = coordinates.lat,
                lon = coordinates.lon
            ).body()?.toForecastList()?.let { entity ->
                emit(ResponseResult.Success(entity))
            } ?: run {
                throw NotFoundException()
            }
        } catch (ex: Exception) {
            emit(ResponseResult.Error(ex))
        }
    }
}