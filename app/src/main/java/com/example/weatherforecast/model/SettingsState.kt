package com.example.weatherforecast.model

data class SettingsState(
    val selectedUnitIndex: Int = 0,
    val selectedLanguageIndex: Int = 0,
    val availableLanguages: List<String> = emptyList(),
    val availableUnits: List<String> = emptyList(),
    val versionInfo: String = "",
    val error: Throwable? = null
)