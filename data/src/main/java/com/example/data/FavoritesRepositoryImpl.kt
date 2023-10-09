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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val weatherService: CurrentWeatherService,
    private val settingsRepository: SettingsRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : FavoritesRepository {
    override  fun getFavoritesFlow(): Flow<List<FavoritesEntity>?> {
        return appDatabase.favoritesDao().observeFavorites().map {
            withContext(dispatcher) {
                val settings = settingsRepository.getSettingsFlow().first()
                val favoritesList = mutableListOf<FavoritesEntity>()
                it.map {
                    async {
                        weatherService.getCurrentWeatherByCity(
                            city = it.city,
                            language = settings.selectedLanguage,
                            units = settings.selectedUnits
                        ).getResult()
                    }
                }.awaitAll().forEach {
                    favoritesList.add(it.toFavoritesEntity())
                }

                return@withContext favoritesList
            }
        }
    }

    override suspend fun refreshFavorites(): List<FavoritesEntity> =
        withContext(dispatcher) {
            val favoritesList = mutableListOf<FavoritesEntity>()
            appDatabase.favoritesDao().getFavorites().map {
                val settings = settingsRepository.getSettingsFlow().first()
                async {
                    weatherService.getCurrentWeatherByCity(
                        city = it.city,
                        language = settings.selectedLanguage,
                        units = settings.selectedUnits
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
        if (appDatabase.weatherDao().observeWeather().first()?.id == id)
        appDatabase.weatherDao().updateFavorites(
            UpdateFavoritesTuple(
                weatherKey = Const.WEATHER_KEY,
                isFavorites = false
            )
        )
    }
}