package com.example.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.data.entity.dbentity.WeatherDbEntity
import com.example.data.mappers.toAirEntity
import com.example.data.mappers.toForecastList
import com.example.data.mappers.toWeatherDbEntity
import com.example.data.source.services.AirService
import com.example.data.source.db.AppDatabase
import com.example.data.source.services.ForecastService
import com.example.data.source.services.CurrentWeatherService
import com.example.data.entity.City
import com.example.data.entity.Coordinates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val currentWeatherService: CurrentWeatherService,
    private val forecastService: ForecastService,
    private val airService: AirService,
    private val appDatabase: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {
    override fun getWeatherFlow(): Flow<WeatherDbEntity?> {
        return appDatabase.weatherDao().getWeatherFlow()
    }

    @WorkerThread
    override suspend fun loadWeatherByCoordinates(coordinates: Coordinates) =
        wrapBackendExceptions {
            val weather = currentWeatherService.getCurrentWeatherByCoordinates(
                lat = coordinates.lat, lon = coordinates.lon
            )
            val forecast = forecastService.getForecastByCoordinate(
                lat = coordinates.lat, lon = coordinates.lon
            )
            val air = airService.getAirByCoordinate(
                lat = coordinates.lat, lon = coordinates.lon
            )

            wrapSQLiteException(ioDispatcher) {
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
    override suspend fun loadWeatherByCity(city: City) = wrapBackendExceptions {
        try {
            currentWeatherService.getCurrentWeatherByCity(city.city)
        } catch (e: Exception) {
            Log.e("aaaaaC", e.message.toString())
            Log.e("aaaaaC", e.localizedMessage)
            Log.e("aaaaaC", e.cause?.message.toString())
            Log.e("aaaaaC", e.suppressed.toString())
        }
        val weather = currentWeatherService.getCurrentWeatherByCity(city.city)
        val forecast = forecastService.getForecastByCity(city.city)
        val air = airService.getAirByCoordinate(
            lat = weather.coord.lat.toString(), lon = weather.coord.lon.toString()
        )

        wrapSQLiteException(ioDispatcher) {
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