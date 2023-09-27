package com.example.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.source.local.entity.FavoritesDbEntity
import com.example.data.source.local.entity.WeatherDbEntity
import com.example.data.source.local.dao.FavoritesDao
import com.example.data.source.local.dao.WeatherDao

@Database(
    entities = [
        WeatherDbEntity::class,
        FavoritesDbEntity::class
    ], version = 1
)
@TypeConverters(ForecastConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    abstract fun favoritesDao(): FavoritesDao
}