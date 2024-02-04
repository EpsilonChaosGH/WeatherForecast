package com.example.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.entity.SettingsState
import com.example.data.utils.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val PREF_LANGUAGE by lazy { stringPreferencesKey("language") }
    private val PREF_UNITS by lazy { stringPreferencesKey("units") }

    override fun getSettingsFlow(): Flow<SettingsState> {
        return get(key = PREF_UNITS, default = "metric").combine(
            get(key = PREF_LANGUAGE, default = "en")
        ) { units, language ->
            Log.e("aaa", "$units + $language")
            SettingsState(
                selectedUnits = units,
                selectedLanguage = language,
                versionInfo = getAppVersion()
            )
        }
    }

    override suspend fun setLanguage(language: String) {
        set(key = PREF_LANGUAGE, value = language)
    }

    override suspend fun setUnits(units: String) {
        set(key = PREF_UNITS, value = units)
    }

    private fun getAppVersion(): String = "Version: 1.0"

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