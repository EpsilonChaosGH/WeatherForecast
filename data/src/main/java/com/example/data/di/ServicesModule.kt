package com.example.data.di

import com.example.data.source.network.services.AirService
import com.example.data.source.network.services.ForecastService
import com.example.data.source.network.services.CurrentWeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun providesServiceWeather(retrofit: Retrofit): CurrentWeatherService {
        return retrofit.create(CurrentWeatherService::class.java)
    }

    @Provides
    @Singleton
    fun providesServiceForecast(retrofit: Retrofit): ForecastService {
        return retrofit.create(ForecastService::class.java)
    }

    @Provides
    @Singleton
    fun providesServiceAir(retrofit: Retrofit): AirService {
        return retrofit.create(AirService::class.java)
    }
}