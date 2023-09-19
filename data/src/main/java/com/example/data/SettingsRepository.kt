package com.example.data

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLanguageIndex(language: Int)

    suspend fun getLanguageIndex(): Flow<Int>

    suspend fun getLanguage(): String

    suspend fun setUnitsIndex(units: Int)

    suspend fun getUnitsIndex(): Flow<Int>

    suspend fun getUnits(): String

    fun getAppVersion(): String

    fun getAvailableLanguages(): List<String>

    fun getAvailableUnits(): List<String>

//    suspend fun setDefaultLocation(defaultLocation: DefaultLocation)

//    suspend fun getDefaultLocation(): Flow<DefaultLocation>
}
