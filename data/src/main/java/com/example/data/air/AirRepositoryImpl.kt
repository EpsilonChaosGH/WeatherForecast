package com.example.data.air

import com.example.data.NotFoundException
import com.example.data.mappers.toAirEntity
import com.example.data.weather.WeatherService
import com.example.domain.ResponseResult
import com.example.domain.air.AirRepository
import com.example.domain.entity.AirEntity
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.executeWithResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AirRepositoryImpl @Inject constructor(
    private val airService: AirService,
    private val weatherService: WeatherService
) : AirRepository {
    override suspend fun loadingAirByCity(city: City): ResponseResult<AirEntity> {
        return executeWithResponse {
            val coordinates = getCityByCoordinates(city)
            airService.getAirPollutionByCoordinate(
                lon = coordinates.lon,
                lat = coordinates.lat
            ).body()
                ?.toAirEntity()
                ?: throw NotFoundException()
        }
    }

    override suspend fun loadingAirByCoordinates(coordinates: Coordinates): ResponseResult<AirEntity> {
        return executeWithResponse {
            airService.getAirPollutionByCoordinate(
                lon = coordinates.lon,
                lat = coordinates.lat
            ).body()
                ?.toAirEntity()
                ?: throw NotFoundException()
        }
    }

    private suspend fun getCityByCoordinates(city: City): Coordinates {
        val coordinates = weatherService.getWeatherByCity(city.city).body()?.coord
        return Coordinates(
            lat = coordinates?.lat.toString(),
            lon = coordinates?.lon.toString()
        )
    }
}