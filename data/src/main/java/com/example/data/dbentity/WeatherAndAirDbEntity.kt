package com.example.data.dbentity

import androidx.room.*

@Entity(tableName = "main_weather")
data class WeatherAndAirDbEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    //@ColumnInfo(name = "is_current") var isCurrent: Boolean,
    @ColumnInfo(name = "is_favorites") var isFavorites: Boolean,
    @Embedded val weather: WeatherDbEntity,
    @Embedded val air: AirDbEntity
)