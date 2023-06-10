package com.example.data

import android.util.Log
import com.example.data.mappers.toForecastDbEntity
import com.example.data.mappers.toWeatherAndAirDbEntity
import com.example.data.mappers.toMainWeatherEntity
import com.example.data.weather.WeatherDao
import com.example.domain.WeatherDbRepository
import com.example.domain.entity.ForecastEntity
import com.example.domain.entity.MainWeatherEntity
import com.example.domain.entity.WeatherAndAirEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherDbRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherDbRepository {

    override fun getMainWeatherFlow(): Flow<MainWeatherEntity?> {
        return weatherDao.getMainWeatherFlow().map {
            it?.toMainWeatherEntity()
        }
    }

    override suspend fun insertMainWeather(weather: WeatherAndAirEntity) {
        weatherDao.insertMainWeather(weather.toWeatherAndAirDbEntity())
    }

    override suspend fun insertForecast(forecast: List<ForecastEntity>) {
        weatherDao.insertForecast(forecast.map { it.toForecastDbEntity() })
    }
}