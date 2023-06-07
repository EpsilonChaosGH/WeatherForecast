package com.example.weatherforecast.di

import com.example.domain.weather.GetWeatherByCityUseCase
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
    fun getWeatherByCityUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherByCityUseCase {
        return GetWeatherByCityUseCase(weatherRepository)
    }
}