package com.example.data

import com.example.data.entity.FavoritesEntity
import com.example.data.source.local.entity.FavoritesDbEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface FavoritesRepository {

    val favoritesFlow: MutableStateFlow<List<FavoritesEntity>>

    suspend fun getFavoritesFlow()

    suspend fun refreshFavorites()

    suspend fun addToFavorites(favorites: FavoritesDbEntity)

    suspend fun deleteFromFavoritesById(id: Long)
}