package com.example.domain.entity


data class ForecastEntity(
    val city_id: Long,
    val city: String,
    val temperature: Double,
    val icon: String,
    val data: Long,
)