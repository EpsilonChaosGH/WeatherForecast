package com.example.domain.air

import com.example.domain.ResponseResult
import com.example.domain.entity.AirEntity
import com.example.domain.entity.City
import com.example.domain.entity.Coordinates
import com.example.domain.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow

interface AirRepository {

    fun loadingAirByCity(city: City): Flow<ResponseResult<AirEntity>>

    fun loadingAirByCoordinates(coordinates: Coordinates): Flow<ResponseResult<AirEntity>>

}