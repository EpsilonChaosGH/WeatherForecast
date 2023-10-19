package com.example.data

import com.example.data.entity.FavoritesEntity
import com.example.data.source.local.entity.FavoritesDbEntity
import kotlinx.coroutines.flow.StateFlow

interface FavoritesRepository {

    val favoritesFlow: StateFlow<List<FavoritesEntity>>

    suspend fun observeFavorites()

    suspend fun refreshFavorites()

    suspend fun addToFavorites(favorites: FavoritesDbEntity)

    suspend fun deleteFromFavoritesById(id: Long)
}