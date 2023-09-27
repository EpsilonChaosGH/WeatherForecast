package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.data.entity.SettingsState
import com.example.data.utils.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val PREF_UNITS by lazy { intPreferencesKey("units") }
    private val PREF_LANGUAGE by lazy { intPreferencesKey("language") }
    override fun getSettingsFlow(): Flow<SettingsState> {
        return combine(
            get(key = PREF_UNITS, default = 0),
            get(key = PREF_LANGUAGE, default = 0)
        ) { unitsIndex, languageIndex ->
            SettingsState(
                selectedUnitIndex = unitsIndex,
                selectedLanguageIndex = languageIndex,
                versionInfo = getAppVersion()
            )
        }
    }

    override suspend fun setLanguageIndex(language: Int) {
        set(key = PREF_LANGUAGE, value = language)
    }

    override suspend fun setUnitsIndex(units: Int) {
        set(key = PREF_UNITS, value = units)
    }

    private fun getAppVersion(): String = "Version: ???"

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