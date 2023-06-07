package com.example.weatherforecast.di

import com.example.data.weather.WeatherRepositoryImpl
import com.example.domain.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetWeatherByCityUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun getWeatherByCityUseCaseBinds(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}