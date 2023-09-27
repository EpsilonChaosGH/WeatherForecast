package com.example.data

import androidx.annotation.WorkerThread
import com.example.data.di.DefaultDispatcher
import com.example.data.source.local.entity.WeatherDbEntity
import com.example.data.mappers.toAirEntity
import com.example.data.mappers.toForecastList
import com.example.data.mappers.toWeatherDbEntity
import com.example.data.source.network.services.AirService
import com.example.data.source.local.AppDatabase
import com.example.data.source.network.services.ForecastService
import com.example.data.source.network.services.CurrentWeatherService
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import com.example.data.entity.WeatherEntity
import com.example.data.mappers.toWeatherEntity
import com.example.data.utils.getResult
import com.example.data.utils.wrapBackendExceptions
import com.example.data.utils.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val currentWeatherService: CurrentWeatherService,
    private val forecastService: ForecastService,
    private val airService: AirService,
    private val appDatabase: AppDatabase,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : WeatherRepository {
    override fun getWeatherFlow(): Flow<WeatherEntity?> {
        return appDatabase.weatherDao().observeWeather().map { weatherDbEntity ->
            weatherDbEntity?.toWeatherEntity()
        }
    }

    @WorkerThread
    override suspend fun loadWeatherByCoordinates(coordinates: Coordinates, units: String, language: String) =
        wrapBackendExceptions {
            val weather = currentWeatherService.getCurrentWeatherByCoordinates(
                lat = coordinates.lat,
                lon = coordinates.lon,
                units = units,
                language = language
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

    @WorkerThread
    override suspend fun loadWeatherByCity(city: City, units: String, language: String) = wrapBackendExceptions {
        val weather = currentWeatherService.getCurrentWeatherByCity(
            city = city.city,
            units = units,
            language = language
        ).getResult()
        val forecast = forecastService.getForecastByCity(city.city).getResult()
        val air = airService.getAirByCoordinate(
            lat = weather.coord.lat.toString(), lon = weather.coord.lon.toString()
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