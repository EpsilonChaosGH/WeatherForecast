package com.example.data.source.network.services

import com.example.data.utils.Const
import com.example.data.source.network.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {

    @GET("data/2.5/forecast?")
    suspend fun getForecastByCoordinate(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("lang") language: String = Const.LANG_EN,
        @Query("units") units: String = Const.UNITS_METRIC,
        @Query("cnt") cnt: String = Const.CNT
    ): Response<ForecastResponse>
//
//    @GET("data/2.5/forecast?")
//    suspend fun getForecastByCity(
//        @Query("q") city: String,
//        @Query("appid") appId: String = Const.APP_ID,
//        @Query("lang") language: String = Const.LANG_EN,
//        @Query("units") units: String = Const.UNITS_METRIC,
//        @Query("cnt") cnt: String = Const.CNT
//    ): Response<ForecastResponse>
}