package com.example.domain.forecast

import com.example.domain.ResponseResult
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    fun loadingForecastByCity(city: City): Flow<ResponseResult<List<ForecastEntity>>>

    fun loadingForecastByCoordinates(coordinates: Coordinates): Flow<ResponseResult<List<ForecastEntity>>>

}