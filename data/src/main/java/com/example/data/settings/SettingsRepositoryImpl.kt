package com.example.data.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.data.SettingsRepository
import com.example.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val PREF_LANGUAGE by lazy { intPreferencesKey("language") }
    private val PREF_UNITS by lazy { intPreferencesKey("units") }


    override suspend fun setLanguageIndex(language: Int) {
        set(key = PREF_LANGUAGE, value = language)
    }

    override suspend fun getLanguageIndex(): Flow<Int> =
        get(key = PREF_LANGUAGE, default = 0)


    override suspend fun setUnitsIndex(units: Int) {
        set(key = PREF_UNITS, value = units)
    }

    override suspend fun getUnitsIndex(): Flow<Int> =
        get(key = PREF_UNITS, default = 0)

    override fun getAppVersion(): String = "Version: ???"

    private suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    private fun <T> get(key: Preferences.Key<T>, default: T): Flow<T> {
        return context.dataStore.data.map { settings ->
            settings[key] ?: default
        }
    }
}