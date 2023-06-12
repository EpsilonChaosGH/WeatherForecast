package com.example.weatherforecast.di

import com.example.data.WeatherDbRepositoryImpl
import com.example.data.air.AirRepositoryImpl
import com.example.data.forecast.ForecastRepositoryImpl
import com.example.data.weather.WeatherRepositoryImpl
import com.example.domain.WeatherDbRepository
import com.example.domain.air.AirRepository
import com.example.domain.forecast.ForecastRepository
import com.example.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsAirRepository(
        airRepositoryImpl: AirRepositoryImpl
    ): AirRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsForecastRepository(
        forecastRepositoryImpl: ForecastRepositoryImpl
    ): ForecastRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsWeatherDbRepository(
        weatherDbRepositoryImpl: WeatherDbRepositoryImpl
    ): WeatherDbRepository
}