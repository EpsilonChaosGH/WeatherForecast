package com.example.data.source.network.services

import com.example.data.utils.Const
import com.example.data.source.network.response.AirResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirService {

    @GET("data/2.5/air_pollution?")
    suspend fun getAirByCoordinate(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
    ): Response<AirResponse>
}