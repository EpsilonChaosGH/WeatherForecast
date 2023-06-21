package com.example.data

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
            ).getResult()
            val forecast = forecastService.getForecastByCoordinate(
                lat = coordinates.lat, lon = coordinates.lon
            ).getResult()
            val air = airService.getAirByCoordinate(
                lat = coordinates.lat, lon = coordinates.lon
            ).getResult()

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

        val weather = currentWeatherService.getCurrentWeatherByCity(city.city).getResult()
        val forecast = forecastService.getForecastByCity(city.city).getResult()
        val air = airService.getAirByCoordinate(
            lat = weather.coord.lat.toString(), lon = weather.coord.lon.toString()
        ).getResult()

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