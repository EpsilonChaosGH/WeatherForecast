package com.example.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.entity.dbentity.FavoritesDbEntity
import com.example.data.entity.dbentity.WeatherDbEntity

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