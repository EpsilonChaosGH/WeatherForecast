package com.example.data.entity.response

import com.squareup.moshi.Json


data class CurrentWeatherResponse(
    val id: Long,
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val name: String,
) {

    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Main(
        @field:Json(name = "temp")
        val temp: Double,

        @field:Json(name = "feels_like")
        val feelsLike: Double,

        @field:Json(name = "pressure")
        val pressure: Long,

        @field:Json(name = "humidity")
        val humidity: Long,
    )

    data class Sys(
        val country: String,
    )

    data class Weather(
        val main: String,
        val description: String,
        val icon: String
    )

    data class Wind(
        val speed: Double,
    )
}