package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.source.db.AppDatabase
import com.example.data.source.db.FavoritesDao
import com.example.data.source.db.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "WeatherApp-DB"
        )
            .build()

    @Provides
    @Singleton
    fun weatherDao(db: AppDatabase): WeatherDao = db.weatherDao()

    @Provides
    @Singleton
    fun favoritesDao(db: AppDatabase): FavoritesDao = db.favoritesDao()
}