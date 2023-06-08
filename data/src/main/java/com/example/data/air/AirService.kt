package com.example.data.air

import com.example.data.Const
import com.example.data.entity.AirResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirService {

    @GET("air_pollution?")
    suspend fun getAirPollutionByCoordinate(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
    ): Response<AirResponse>
}