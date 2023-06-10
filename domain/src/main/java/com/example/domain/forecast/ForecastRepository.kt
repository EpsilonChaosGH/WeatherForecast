package com.example.domain.forecast

import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    suspend fun loadingForecastByCity(city: City): ResponseResult<List<ForecastEntity>>

    suspend fun loadingForecastByCoordinates(coordinates: Coordinates): ResponseResult<List<ForecastEntity>>

}