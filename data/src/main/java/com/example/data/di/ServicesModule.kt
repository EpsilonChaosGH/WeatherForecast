package com.example.data.di

import com.example.data.weather.WeatherService
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
    fun providesServiceUser(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}