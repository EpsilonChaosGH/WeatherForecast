package com.example.domain.weather

import com.example.domain.WeatherDbRepository
import com.example.domain.air.AirRepository
import com.example.domain.entity.Coordinates
import com.example.domain.entity.MainWeatherEntity
import com.example.domain.forecast.ForecastRepository
import com.example.domain.getResult

class LoadMainWeatherByCoordinatesUseCase(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository,
    private val airRepository: AirRepository,
    private val weatherDbRepository: WeatherDbRepository
) {

    suspend fun loadingMainWeatherByCity(coordinates: Coordinates) {

        val weather = weatherRepository.loadingWeatherByCoordinates(coordinates).getResult()
        val air = airRepository.loadingAirByCoordinates(coordinates).getResult()
        val forecast = forecastRepository.loadingForecastByCoordinates(coordinates).getResult()

        weatherDbRepository.insertMainWeather(
            MainWeatherEntity(
                id = weather.id,
                isFavorites = false,
                weatherEntity = weather,
                airEntity = air,
                forecastEntityList = forecast
            )
        )
    }
}