package com.example.data.forecast

import com.example.data.NotFoundException
import com.example.data.mappers.toForecastList
import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.ForecastEntity
import com.example.domain.executeWithResponse
import com.example.domain.forecast.ForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastService: ForecastService
) : ForecastRepository {
    override suspend fun loadingForecastByCity(city: City): ResponseResult<List<ForecastEntity>> {
        return executeWithResponse {
            forecastService.getForecastByCity(city.city).body()
                ?.toForecastList()
                ?: throw NotFoundException()
        }
    }

    override suspend fun loadingForecastByCoordinates(coordinates: Coordinates): ResponseResult<List<ForecastEntity>> {
        return executeWithResponse {
            forecastService.getForecastByCoordinate(
                lat = coordinates.lat,
                lon = coordinates.lon
            ).body()
                ?.toForecastList()
                ?: throw NotFoundException()
        }
    }
}