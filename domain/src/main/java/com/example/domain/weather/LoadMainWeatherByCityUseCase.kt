package com.example.domain.weather

import com.example.domain.WeatherDbRepository
import com.example.domain.air.AirRepository
import com.example.domain.entity.City
import com.example.domain.entity.MainWeatherEntity
import com.example.domain.entity.WeatherAndAirEntity
import com.example.domain.forecast.ForecastRepository
import com.example.domain.getResult

class LoadMainWeatherByCityUseCase(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository,
    private val airRepository: AirRepository,
    private val weatherDbRepository: WeatherDbRepository
) {

    suspend fun loadingMainWeatherByCity(city: City) {

        val weather = weatherRepository.loadingWeatherByCity(city).getResult()
        val air = airRepository.loadingAirByCity(city).getResult()
        val forecast = forecastRepository.loadingForecastByCity(city).getResult()

        weatherDbRepository.insertMainWeather(
            WeatherAndAirEntity(
                id = weather.id,
                isFavorites = false,
                weatherEntity = weather,
                airEntity = air
            )
        )

        weatherDbRepository.insertForecast(forecast)
    }
}