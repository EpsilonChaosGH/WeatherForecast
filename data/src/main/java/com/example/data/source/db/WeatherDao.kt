package com.example.data.source.db

import androidx.room.*
import com.example.data.entity.dbentity.WeatherDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather where weather_key = 'WEATHER_KEY' ")
    fun getWeatherFlow(): Flow<WeatherDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDbEntity)

    @Update(entity = WeatherDbEntity::class)
    suspend fun updateFavorites(isFavorites: UpdateFavoritesTuple)

}