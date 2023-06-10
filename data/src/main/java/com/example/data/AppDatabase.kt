package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dbentity.ForecastDbEntity
import com.example.data.dbentity.WeatherAndAirDbEntity
import com.example.data.weather.WeatherDao

@Database(
    entities = [
        WeatherAndAirDbEntity::class,
        ForecastDbEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}