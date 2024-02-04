package com.example.data

import com.example.data.di.DefaultDispatcher
import com.example.data.entity.FavoritesEntity
import com.example.data.entity.SettingsState
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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

    private val _favoritesFlow = MutableStateFlow<List<FavoritesEntity>>(listOf())
    override val favoritesFlow: StateFlow<List<FavoritesEntity>> = _favoritesFlow.asStateFlow()

    private suspend fun getFavorites(favorites: List<FavoritesDbEntity>, settings: SettingsState) =
        withContext(dispatcher) {
            val favoritesList = mutableListOf<FavoritesEntity>()
            favorites.map {
                async {
                    weatherService.getCurrentWeatherByCoordinates(
                        lon = it.lon,
                        lat = it.lat,
                        language = settings.selectedLanguage,
                        units = settings.selectedUnits
                    ).getResult()
                }
            }.awaitAll().forEach {
                favoritesList.add(it.toFavoritesEntity(settings))
            }
            return@withContext favoritesList
        }

    override suspend fun observeFavorites() {
        combine(
            appDatabase.favoritesDao().observeFavorites(),
            settingsRepository.getSettingsFlow()
        ) { favorites, settings ->
            _favoritesFlow.value = getFavorites(favorites, settings)
        }.collect()
    }

    override suspend fun refreshFavorites() = withContext(dispatcher) {
        val result = appDatabase.favoritesDao().observeFavorites().first()
        val settings = settingsRepository.getSettingsFlow().first()
        _favoritesFlow.value = getFavorites(result, settings)
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