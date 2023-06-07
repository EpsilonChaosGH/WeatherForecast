package com.example.data.weather

import com.example.data.Const
import com.example.data.entity.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather?")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("units") units: String = Const.UNITS_METRIC
    ): Response<WeatherResponse>

    @GET("weather?")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = Const.APP_ID,
        @Query("units") units: String = Const.UNITS_METRIC
    ): Response<WeatherResponse>
}