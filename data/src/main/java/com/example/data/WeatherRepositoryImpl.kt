package com.example.data

import androidx.annotation.WorkerThread
import com.example.data.di.DefaultDispatcher
import com.example.data.mappers.toAirEntity
import com.example.data.mappers.toForecastList
import com.example.data.mappers.toWeatherDbEntity
import com.example.data.source.network.services.AirService
import com.example.data.source.local.AppDatabase
import com.example.data.source.network.services.ForecastService
import com.example.data.source.network.services.CurrentWeatherService
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.data.entity.SettingsState
import com.example.data.entity.WeatherEntity
import com.example.data.mappers.toCoordinates
import com.example.data.mappers.toWeatherEntity
import com.example.data.source.network.services.GeocodingService
import com.example.data.utils.Const
import com.example.data.utils.getResult
import com.example.data.utils.wrapBackendExceptions
import com.example.data.utils.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val geocodingService: GeocodingService,
    private val currentWeatherService: CurrentWeatherService,
    private val forecastService: ForecastService,
    private val airService: AirService,
    private val appDatabase: AppDatabase,
    private val settingsRepository: SettingsRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : WeatherRepository {
    override fun getWeatherFlow(): Flow<WeatherEntity?> {
        return appDatabase.weatherDao().observeWeather().map { weatherDbEntity ->
            weatherDbEntity?.toWeatherEntity()
        }
    }

    @WorkerThread
    override suspend fun listenWeather() = wrapBackendExceptions {
        settingsRepository.getSettingsFlow().collect { settings ->
            val weather = appDatabase.weatherDao().observeWeather().first()
            loadWeather(
                Coordinates(
                    lon = weather?.lon ?: Const.DEFAULT_LON,
                    lat = weather?.lat ?: Const.DEFAULT_LAT
                ), settings
            )
        }
    }

    @WorkerThread
    override suspend fun loadWeatherByCoordinates(coordinates: Coordinates) =
        wrapBackendExceptions {
            val settings = settingsRepository.getSettingsFlow().first()
            loadWeather(coordinates, settings)
        }

    @WorkerThread
    override suspend fun loadWeatherByCity(city: City) = wrapBackendExceptions {
        val settings = settingsRepository.getSettingsFlow().first()
        val coordinates =
            geocodingService.getCoordinatesByCity(city.city).getResult().toCoordinates()
        loadWeather(coordinates, settings)
    }

    @WorkerThread
    private suspend fun loadWeather(coordinates: Coordinates, settings: SettingsState) =
        wrapBackendExceptions {
            val weather = currentWeatherService.getCurrentWeatherByCoordinates(
                lat = coordinates.lat,
                lon = coordinates.lon,
                language = settings.selectedLanguage,
                units = settings.selectedUnits
            ).getResult()
            val forecast = forecastService.getForecastByCoordinate(
                lat = coordinates.lat, lon = coordinates.lon
            ).getResult()
            val air = airService.getAirByCoordinate(
                lat = coordinates.lat, lon = coordinates.lon
            ).getResult()

            wrapSQLiteException(dispatcher) {
                appDatabase.weatherDao().insertWeather(
                    weather.toWeatherDbEntity(
                        isFavorites = appDatabase.favoritesDao().checkForFavorites(weather.id),
                        forecast = forecast.toForecastList(),
                        air = air.toAirEntity()
                    )
                )
            }
        }
}