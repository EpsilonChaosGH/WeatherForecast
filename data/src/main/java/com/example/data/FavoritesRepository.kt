package com.example.data

import com.example.data.entity.dbentity.FavoritesDbEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoritesFlow(): Flow<List<FavoritesDbEntity?>>

    suspend fun addToFavorites(favorites: FavoritesDbEntity)

    suspend fun deleteFromFavoritesById(id: Long)
}