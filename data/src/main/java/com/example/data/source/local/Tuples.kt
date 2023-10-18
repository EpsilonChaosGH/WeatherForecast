package com.example.data.source.local

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class UpdateFavoritesTuple(
    @ColumnInfo(name = "weather_key") @PrimaryKey val weatherKey: String,
    @ColumnInfo(name = "is_favorites") var isFavorites: Boolean,
)