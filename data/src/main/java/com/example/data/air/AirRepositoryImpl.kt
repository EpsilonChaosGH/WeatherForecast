package com.example.data.air

import com.example.data.NotFoundException
import com.example.data.mappers.toAirEntity
import com.example.data.weather.WeatherService
import com.example.domain.ResponseResult
import com.example.domain.air.AirRepository
import com.example.domain.entity.AirEntity
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AirRepositoryImpl @Inject constructor(
    private val airService: AirService,
    private val weatherService: WeatherService
) : AirRepository {
    override fun loadingAirByCity(city: City) = flow {
        try {
            val coordinates = getCityByCoordinates(city)
            airService.getAirPollutionByCoordinate(
                lat = coordinates.lat,
                lon = coordinates.lon
            ).body()?.toAirEntity()?.let { entity ->
                emit(ResponseResult.Success(entity))
            } ?: run {
                throw NotFoundException()
            }
        } catch (ex: Exception) {
            emit(ResponseResult.Error(ex))
        }
    }

    override fun loadingAirByCoordinates(coordinates: Coordinates) = flow {
        try {
            airService.getAirPollutionByCoordinate(
                lat = coordinates.lat,
                lon = coordinates.lon
            ).body()?.toAirEntity()?.let { entity ->
                emit(ResponseResult.Success(entity))
            } ?: run {
                throw NotFoundException()
            }
        } catch (ex: Exception) {
            emit(ResponseResult.Error(ex))
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