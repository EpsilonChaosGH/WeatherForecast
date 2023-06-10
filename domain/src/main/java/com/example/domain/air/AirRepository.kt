package com.example.domain.air

import com.example.domain.ResponseResult
import com.example.domain.entity.AirEntity
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow

interface AirRepository {

    suspend fun loadingAirByCity(city: City): ResponseResult<AirEntity>

    suspend fun loadingAirByCoordinates(coordinates: Coordinates): ResponseResult<AirEntity>

}