package com.example.data.entity

import com.example.data.utils.Const

data class SettingsState(
    val selectedLanguage: String = Const.LANG_EN,
    val selectedUnits: String = Const.UNITS_METRIC,
    val versionInfo: String = "",
//    val availableLanguages: List<String> = emptyList(),
//    val availableUnits: List<String> = emptyList(),
    // val error: Throwable? = null
)