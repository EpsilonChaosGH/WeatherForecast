package com.example.weatherforecast.model

data class SettingsState(
    val selectedUnit: String = "",
    val selectedLanguage: String = "",
    val availableLanguages: List<String> = emptyList(),
    val availableUnits: List<String> = emptyList(),
    val versionInfo: String = "",
    val error: Throwable? = null
)