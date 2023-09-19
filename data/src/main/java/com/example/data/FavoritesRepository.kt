package com.example.data

import com.example.data.entity.FavoritesEntity
import com.example.data.entity.dbentity.FavoritesDbEntity
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Language

interface FavoritesRepository {

    suspend fun getFavoritesFlow(): Flow<List<FavoritesEntity>?>

    suspend fun refreshFavorites(units: String, language: String): List<FavoritesEntity>

    suspend fun addToFavorites(favorites: FavoritesDbEntity)

    suspend fun deleteFromFavoritesById(id: Long)
}