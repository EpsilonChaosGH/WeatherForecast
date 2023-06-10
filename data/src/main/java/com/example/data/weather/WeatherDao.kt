package com.example.data.weather

import androidx.room.*
import com.example.data.dbentity.ForecastDbEntity
import com.example.data.dbentity.WeatherAndAirDbEntity
import com.example.data.dbentity.MainWeatherDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM main_weather")
    fun getMainWeatherFlow(): Flow<MainWeatherDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainWeather(weather: WeatherAndAirDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: List<ForecastDbEntity>)

}