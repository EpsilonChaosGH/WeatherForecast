package com.example.data

import com.example.data.entity.SettingsState
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettingsFlow(): Flow<SettingsState>

    suspend fun setLanguageIndex(language: Int)

    suspend fun setUnitsIndex(units: Int)
}