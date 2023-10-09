package com.example.data.source.network.services

import com.example.data.utils.Const
import com.example.data.source.network.response.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {

    @GET("weather?")
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("lang") language: String = Const.LANG_EN,
        @Query("units") units: String = Const.UNITS_METRIC
    ): Response<CurrentWeatherResponse>

    @GET("weather?")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("lang") language: String = Const.LANG_EN,
        @Query("units") units: String = Const.UNITS_METRIC
    ): Response<CurrentWeatherResponse>
}