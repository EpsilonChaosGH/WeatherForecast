package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.dbentity.MainWeatherDbEntity
import com.example.data.weather.WeatherDao

@Database(
    entities = [
        MainWeatherDbEntity::class
    ], version = 1
)
@TypeConverters(ForecastConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}