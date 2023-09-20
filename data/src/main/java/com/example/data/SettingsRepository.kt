package com.example.data

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLanguageIndex(language: Int)

    suspend fun getLanguageIndex(): Flow<Int>

    suspend fun setUnitsIndex(units: Int)

    suspend fun getUnitsIndex(): Flow<Int>

    fun getAppVersion(): String

}
