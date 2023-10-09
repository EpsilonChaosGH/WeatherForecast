package com.example.data.entity

data class SettingsState(
    val selectedLanguage: String = "",
    val selectedUnits: String = "",
    val availableLanguages: List<String> = emptyList(),
    val availableUnits: List<String> = emptyList(),
    val versionInfo: String = "",
    val error: Throwable? = null
)