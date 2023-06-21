package com.example.data.source.services

import com.example.data.Const
import com.example.data.entity.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {

    @GET("forecast?")
    suspend fun getForecastByCoordinate(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("units") units: String = Const.UNITS_METRIC,
        @Query("cnt") cnt: String = Const.CNT
    ): Response<ForecastResponse>

    @GET("forecast?")
    suspend fun getForecastByCity(
        @Query("q") city: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("units") units: String = Const.UNITS_METRIC,
        @Query("cnt") cnt: String = Const.CNT
    ): Response<ForecastResponse>
}