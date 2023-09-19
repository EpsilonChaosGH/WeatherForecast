package com.example.data.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.SettingsRepository
import com.example.data.SupportedLanguage
import com.example.data.Units
import com.example.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val PREF_LANGUAGE by lazy { intPreferencesKey("language") }
    private val PREF_UNITS by lazy { intPreferencesKey("units") }
    private val PREF_LAT_LNG by lazy { stringPreferencesKey("lat_lng") }

    //Düsseldorf
    private val DEFAULT_LONGITUDE = 6.773456
    private val DEFAULT_LATITUDE = 51.227741

    override suspend fun setLanguageIndex(language: Int) {
        set(key = PREF_LANGUAGE, value = language)
    }

    override suspend fun getLanguageIndex(): Flow<Int> =
        get(key = PREF_LANGUAGE, default = 0)

    override suspend fun getLanguage(): String {
        return SupportedLanguage.values()[get(
            key = PREF_LANGUAGE,
            default = 0
        ).first()].languageValue
    }

    override suspend fun setUnitsIndex(units: Int) {
        set(key = PREF_UNITS, value = units)
    }

    override suspend fun getUnitsIndex(): Flow<Int> =
        get(key = PREF_UNITS, default = 0)

    override suspend fun getUnits(): String {
        return Units.values()[get(
            key = PREF_UNITS,
            default = 0
        ).first()].value
    }

    override fun getAppVersion(): String = "Version: ???"
//    override fun getAppVersion(): String = "Version : ${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"

    override fun getAvailableLanguages(): List<String> =
        SupportedLanguage.values().map { it.languageName }

    override fun getAvailableUnits(): List<String> = Units.values().map { it.value }

//    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
//        set(key = PREF_LAT_LNG, value = "${defaultLocation.latitude}/${defaultLocation.longitude}")
//    }
//
//    override suspend fun getDefaultLocation(): Flow<DefaultLocation> {
//        return get(
//            key = PREF_LAT_LNG,
//            default = "$DEFAULT_LATITUDE/$DEFAULT_LONGITUDE"
//        ).map { latlng ->
//            val latLngList = latlng.split("/").map { it.toDouble() }
//            DefaultLocation(latitude = latLngList[0], longitude = latLngList[1])
//        }
//    }

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
