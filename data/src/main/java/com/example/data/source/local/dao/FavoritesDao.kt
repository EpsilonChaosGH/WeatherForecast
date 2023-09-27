package com.example.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.source.local.entity.FavoritesDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun observeFavorites(): Flow<List<FavoritesDbEntity>>

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<FavoritesDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoritesDbEntity: FavoritesDbEntity)

    @Query("DELETE FROM favorites WHERE city_id = :cityId")
    suspend fun deleteFromFavorites(cityId: Long)

    @Query("SELECT EXISTS(SELECT city_id FROM favorites WHERE city_id =:id)")
    fun checkForFavorites(id: Long): Boolean
}