package com.example.data.mappers

import com.example.data.dbentity.AirDbEntity
import com.example.data.entity.AirResponse
import com.example.domain.entity.AirEntity

internal fun AirResponse.toAirEntity() = AirEntity(
    no2 = list.firstOrNull()?.components?.get("no2") ?: -1.0,
    o3 = list.firstOrNull()?.components?.get("o3") ?: -1.0,
    pm10 = list.firstOrNull()?.components?.get("pm10") ?: -1.0,
    pm25 = list.firstOrNull()?.components?.get("pm2_5") ?: -1.0
)

internal fun AirEntity.toAirDbEntity() = AirDbEntity(
    no2 = no2,
    o3 = o3,
    pm10 = pm10,
    pm25 = pm25
)

internal fun AirDbEntity.toAirEntity() = AirEntity(
    no2 = no2,
    o3 = o3,
    pm10 = pm10,
    pm25 = pm25
)