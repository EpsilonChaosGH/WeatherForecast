package com.example.data.source.services

import com.example.data.Const
import com.example.data.entity.response.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {
    @GET("weather?")
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("units") units: String = Const.UNITS_METRIC
    ): CurrentWeatherResponse

    @GET("weather?")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("units") units: String = Const.UNITS_METRIC
    ): CurrentWeatherResponse
}