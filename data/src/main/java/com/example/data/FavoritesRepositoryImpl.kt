package com.example.data

import com.example.data.entity.dbentity.FavoritesDbEntity
import com.example.data.source.db.AppDatabase
import com.example.data.source.db.UpdateFavoritesTuple
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : FavoritesRepository {
    override fun getFavoritesFlow(): Flow<List<FavoritesDbEntity?>> {
        return appDatabase.favoritesDao().getFavoritesFlow()
    }

    override suspend fun addToFavorites(favorites: FavoritesDbEntity) {
        appDatabase.favoritesDao().addToFavorites(favorites)
        appDatabase.weatherDao().updateFavorites(
            UpdateFavoritesTuple(
                weatherKey = Const.WEATHER_KEY,
                isFavorites = true
            )
        )
    }

    override suspend fun deleteFromFavoritesById(id: Long) {
        appDatabase.favoritesDao().deleteFromFavorites(id)
        appDatabase.weatherDao().updateFavorites(
            UpdateFavoritesTuple(
                weatherKey = Const.WEATHER_KEY,
                isFavorites = false
            )
        )
    }
}