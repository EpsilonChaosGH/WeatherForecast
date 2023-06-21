package com.example.data.source.services

import com.example.data.Const
import com.example.data.entity.response.AirResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirService {

    @GET("air_pollution?")
    suspend fun getAirByCoordinate(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
    ): Response<AirResponse>
}