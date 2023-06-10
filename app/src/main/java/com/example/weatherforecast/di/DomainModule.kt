package com.example.weatherforecast.di

import com.example.domain.WeatherDbRepository
import com.example.domain.air.AirRepository
import com.example.domain.forecast.ForecastRepository
import com.example.domain.weather.ListenMainWeatherUseCase
import com.example.domain.weather.LoadMainWeatherByCityUseCase
import com.example.domain.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideLoadMainWeatherByCityUseCase(
        weatherRepository: WeatherRepository,
        forecastRepository: ForecastRepository,
        airRepository: AirRepository,
        weatherDbRepository: WeatherDbRepository
    ): LoadMainWeatherByCityUseCase {
        return LoadMainWeatherByCityUseCase(
            weatherRepository,
            forecastRepository,
            airRepository,
            weatherDbRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideListenMainWeatherUseCase(
        weatherDbRepository: WeatherDbRepository
    ): ListenMainWeatherUseCase {
        return ListenMainWeatherUseCase(
            weatherDbRepository
        )
    }
}