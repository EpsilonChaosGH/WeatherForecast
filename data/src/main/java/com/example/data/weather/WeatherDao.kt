package com.example.data.weather

import androidx.room.*
import com.example.data.dbentity.MainWeatherDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM main_weather where main_weather_key = 'MAIN_WEATHER_KEY' ")
    fun getMainWeatherByIdFlow(): Flow<MainWeatherDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainWeather(weather: MainWeatherDbEntity)

}