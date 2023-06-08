package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entity.MainWeatherDbEntity

@Database(
    entities = [
        MainWeatherDbEntity::class,
       // ForecastDbEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao()//: WeatherDao
}