package com.example.data

import com.example.data.mappers.toWeatherAndAirDbEntity
import com.example.data.mappers.toMainWeatherEntity
import com.example.data.weather.WeatherDao
import com.example.domain.WeatherDbRepository
import com.example.domain.entity.MainWeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherDbRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherDbRepository {

    override fun getMainWeatherByIdFlow(): Flow<MainWeatherEntity?> {
        return weatherDao.getMainWeatherByIdFlow().map {
            it?.toMainWeatherEntity()
        }
    }

    override suspend fun insertMainWeather(weather: MainWeatherEntity) {
        weatherDao.insertMainWeather(weather.toWeatherAndAirDbEntity())
    }
}