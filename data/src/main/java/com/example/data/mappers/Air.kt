package com.example.data.mappers

import com.example.data.entity.AirResponse
import com.example.domain.entity.AirEntity

fun AirResponse.toAirEntity() = AirEntity(
    no2 = list.firstOrNull()?.components?.get("no2") ?: -1.0,
    o3 = list.firstOrNull()?.components?.get("o3") ?: -1.0,
    pm10 = list.firstOrNull()?.components?.get("pm10") ?: -1.0,
    pm25 = list.firstOrNull()?.components?.get("pm2_5") ?: -1.0
)