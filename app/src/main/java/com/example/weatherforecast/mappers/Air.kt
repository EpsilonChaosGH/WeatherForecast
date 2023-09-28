package com.example.weatherforecast.mappers


import com.example.data.source.local.entity.AirDbEntity
import com.example.weatherforecast.model.AirQuality
import com.example.weatherforecast.model.AirState
import kotlin.math.roundToInt

fun AirDbEntity.toAirState() = AirState(
    no2 = "${no2.roundToInt()} μg/m3",
    no2Quality = checkNo2(no2),
    o3 = "${o3.roundToInt()} μg/m3",
    o3Quality = checkO3(o3),
    pm10 = "${pm10.roundToInt()} μg/m3",
    pm10Quality = checkPm10(pm10),
    pm25 = "${pm25.roundToInt()} μg/m3",
    pm25Quality = checkPm25(pm25),
)

private fun checkNo2(no2: Double): AirQuality {
    return when (no2) {
        in 0.0..50.0 -> AirQuality.GOOD
        in 50.0..100.0 -> AirQuality.FAIR
        in 100.0..200.0 -> AirQuality.MODERATE
        in 200.0..400.0 -> AirQuality.POOR
        in 400.0..1000.0 -> AirQuality.VERY_POOR
        else -> {
            AirQuality.ERROR
        }
    }
}

private fun checkPm10(no2: Double): AirQuality {
    return when (no2) {
        in 0.0..25.0 -> AirQuality.GOOD
        in 25.0..50.0 -> AirQuality.FAIR
        in 50.0..90.0 -> AirQuality.MODERATE
        in 9.0..180.0 -> AirQuality.POOR
        in 180.0..1000.0 -> AirQuality.VERY_POOR
        else -> {
            AirQuality.ERROR
        }
    }
}

private fun checkO3(no2: Double): AirQuality {
    return when (no2) {
        in 0.0..60.0 -> AirQuality.GOOD
        in 60.0..120.0 -> AirQuality.FAIR
        in 120.0..180.0 -> AirQuality.MODERATE
        in 180.0..240.0 -> AirQuality.POOR
        in 240.0..1000.0 -> AirQuality.VERY_POOR
        else -> {
            AirQuality.ERROR
        }
    }
}

private fun checkPm25(no2: Double): AirQuality {
    return when (no2) {
        in 0.0..15.0 -> AirQuality.GOOD
        in 15.0..30.0 -> AirQuality.FAIR
        in 30.0..55.0 -> AirQuality.MODERATE
        in 55.0..110.0 -> AirQuality.POOR
        in 110.0..1000.0 -> AirQuality.VERY_POOR
        else -> {
            AirQuality.ERROR
        }
    }
}