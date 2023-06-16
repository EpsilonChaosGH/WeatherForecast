package com.example.data

import com.example.data.entity.FavoritesEntity
import com.example.data.entity.dbentity.FavoritesDbEntity
import com.example.data.mappers.toFavoritesEntity
import com.example.data.source.db.AppDatabase
import com.example.data.source.db.UpdateFavoritesTuple
import com.example.data.source.services.CurrentWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val weatherService: CurrentWeatherService
) : FavoritesRepository {
    override suspend fun getFavoritesFlow(): Flow<List<FavoritesEntity>?> {
        return appDatabase.favoritesDao().getFavoritesFlow().map {
            withContext(Dispatchers.IO) {
                val favoritesList = mutableListOf<FavoritesEntity>()
                it?.map {
                    async {
                        weatherService.getCurrentWeatherByCity(it.city)
                    }
                }?.awaitAll()?.forEach {
                    favoritesList.add(it.toFavoritesEntity())
                }
                return@withContext favoritesList
            }
        }
    }

    override suspend fun refreshFavorites(): List<FavoritesEntity> = withContext(Dispatchers.IO) {
        val favoritesList = mutableListOf<FavoritesEntity>()
        appDatabase.favoritesDao().getFavorites()?.map {
            async {
                weatherService.getCurrentWeatherByCity(it.city)
            }
        }?.awaitAll()?.forEach {
            favoritesList.add(it.toFavoritesEntity())
        }
        return@withContext favoritesList
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