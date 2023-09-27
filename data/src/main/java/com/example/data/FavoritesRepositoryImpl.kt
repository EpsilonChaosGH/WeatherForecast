package com.example.data

import com.example.data.di.DefaultDispatcher
import com.example.data.entity.FavoritesEntity
import com.example.data.source.local.entity.FavoritesDbEntity
import com.example.data.mappers.toFavoritesEntity
import com.example.data.source.local.AppDatabase
import com.example.data.source.local.UpdateFavoritesTuple
import com.example.data.source.network.services.CurrentWeatherService
import com.example.data.utils.Const
import com.example.data.utils.getResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val weatherService: CurrentWeatherService,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : FavoritesRepository {
    override suspend fun getFavoritesFlow(
        units: String,
        language: String
    ): Flow<List<FavoritesEntity>?> {
        return appDatabase.favoritesDao().observeFavorites().map {
            withContext(dispatcher) {
                val favoritesList = mutableListOf<FavoritesEntity>()
                it.map {
                    async {
                        weatherService.getCurrentWeatherByCity(
                            city = it.city,
                            units = units,
                            language = language
                        ).getResult()
                    }
                }.awaitAll().forEach {
                    favoritesList.add(it.toFavoritesEntity())
                }
                return@withContext favoritesList
            }
        }
    }

    override suspend fun refreshFavorites(units: String, language: String): List<FavoritesEntity> =
        withContext(dispatcher) {
            val favoritesList = mutableListOf<FavoritesEntity>()
            appDatabase.favoritesDao().getFavorites().map {
                async {
                    weatherService.getCurrentWeatherByCity(
                        city = it.city,
                        units = units,
                        language = language
                    ).getResult()
                }
            }.awaitAll().forEach {
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