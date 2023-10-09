package com.example.data

import com.example.data.entity.FavoritesEntity
import com.example.data.source.local.entity.FavoritesDbEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoritesFlow(): Flow<List<FavoritesEntity>?>

    suspend fun refreshFavorites(): List<FavoritesEntity>

    suspend fun addToFavorites(favorites: FavoritesDbEntity)

    suspend fun deleteFromFavoritesById(id: Long)
}