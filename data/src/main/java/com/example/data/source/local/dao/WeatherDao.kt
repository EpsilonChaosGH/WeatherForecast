package com.example.data.source.local.dao

import androidx.room.*
import com.example.data.source.local.entity.WeatherDbEntity
import com.example.data.source.local.UpdateFavoritesTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather where weather_key = 'WEATHER_KEY' ")
    fun observeWeather(): Flow<WeatherDbEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherDbEntity)

    @Update(entity = WeatherDbEntity::class)
    suspend fun updateFavorites(isFavorites: UpdateFavoritesTuple)
}