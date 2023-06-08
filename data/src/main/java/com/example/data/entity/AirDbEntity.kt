package com.example.data.entity

import androidx.room.ColumnInfo


data class AirDbEntity(
    @ColumnInfo(name = "no2") val no2: Double,
    @ColumnInfo(name = "o3") val o3: Double,
    @ColumnInfo(name = "pm10") val pm10: Double,
    @ColumnInfo(name = "pm25") val pm25: Double,
)