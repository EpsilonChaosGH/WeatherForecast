package com.example.data.dbentity

import androidx.room.*
import com.example.domain.entity.ForecastEntity

@Entity(tableName = "main_weather")
data class MainWeatherDbEntity(
    @PrimaryKey @ColumnInfo(name = "main_weather_key") val mainWeatherKey: String,
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "is_favorites") var isFavorites: Boolean,
    @Embedded val weather: WeatherDbEntity,
    @Embedded val air: AirDbEntity,
    @ColumnInfo(name = "forecast") val forecast: List<ForecastEntity>
)