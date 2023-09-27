package com.example.data.source.network.response

import com.squareup.moshi.Json

data class ForecastResponse(
    val list: List<ListElement>,
    val city: City
) {

    data class City(
        val id: Long,
        val name: String,
        val coord: Coord,
        val country: String,
        val timezone: Long,
    )

    data class Coord(
        val lat: Double,
        val lon: Double
    )

    data class ListElement(
        val dt: Long,
        val main: MainClass,
        val weather: List<Weather>,
        val wind: Wind,
    )

    data class MainClass(
        @field:Json(name = "temp")
        val temp: Double,

        @field:Json(name = "feels_like")
        val feelsLike: Double,

        @field:Json(name = "pressure")
        val pressure: Long,

        @field:Json(name = "humidity")
        val humidity: Long,
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