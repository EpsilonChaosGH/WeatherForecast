package com.example.data.dbentity

import androidx.room.*

@Entity(
    tableName = "forecast",
    indices = [
        Index("city_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = WeatherAndAirDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ForecastDbEntity(
    @PrimaryKey @ColumnInfo(name = "city_id") val id: Long,
    @ColumnInfo(name = "city_forecast") val city: String,
    @ColumnInfo(name = "temperature_forecast") val temperature: Double,
    @ColumnInfo(name = "icon_forecast") val icon: String,
    @ColumnInfo(name = "data_forecast") val data: Long,
)