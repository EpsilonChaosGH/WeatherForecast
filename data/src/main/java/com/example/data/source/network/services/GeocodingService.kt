package com.example.data.source.network.services

import com.example.data.source.network.response.GeocodingResponse
import com.example.data.utils.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {

    @GET("geo/1.0/direct?")
    suspend fun getCoordinatesByCity(
        @Query("q") city: String,
//        @Query("appid") appId: String = Const.APP_ID
    ): Response<List<GeocodingResponse>>
}