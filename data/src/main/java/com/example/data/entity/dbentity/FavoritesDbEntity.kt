package com.example.data.entity.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesDbEntity(
    @PrimaryKey @ColumnInfo(name = "city_id") val cityId: Long,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "longitude") val lon: String,
    @ColumnInfo(name = "latitude") val lat: String,
)