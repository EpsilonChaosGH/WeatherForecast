package com.example.domain.entity


data class ForecastEntity(
    val temperature: Double,
    val icon: String,
    val data: Long,
)