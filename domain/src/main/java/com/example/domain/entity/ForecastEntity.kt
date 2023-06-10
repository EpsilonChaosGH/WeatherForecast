package com.example.domain.entity


data class ForecastEntity(
    val id: Long,
    val city: String,
    val temperature: Double,
    val icon: String,
    val data: Long,
)