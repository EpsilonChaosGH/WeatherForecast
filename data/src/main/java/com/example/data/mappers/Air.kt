package com.example.data.mappers

import com.example.data.source.local.entity.AirDbEntity
import com.example.data.source.network.response.AirResponse

internal fun AirResponse.toAirEntity() = AirDbEntity(
    no2 = list.firstOrNull()?.components?.get("no2") ?: -1.0,
    o3 = list.firstOrNull()?.components?.get("o3") ?: -1.0,
    pm10 = list.firstOrNull()?.components?.get("pm10") ?: -1.0,
    pm25 = list.firstOrNull()?.components?.get("pm2_5") ?: -1.0
)