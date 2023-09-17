package com.example.weatherforecast.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SettingsRepository
import com.example.data.SupportedLanguage
import com.example.weatherforecast.model.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        listenSettings()
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
        }
    }

    private fun getLanguageValue(language: String) =
        SupportedLanguage.values().first { it.languageName == language }.languageValue

    fun setUnits(units: String) {
        viewModelScope.launch {
            settingsRepository.setUnits(units)
        }
    }

    private fun listenSettings() {
        viewModelScope.launch {
            combine(
                settingsRepository.getLanguage(),
                settingsRepository.getUnits()
            ) { language, units ->
                Pair(language, units)
            }.collect { (language, units) ->
                _state.value = _state.value.copy(
                    selectedLanguage = language,
                    selectedUnit = units,
                    versionInfo = settingsRepository.getAppVersion(),
                    availableLanguages = settingsRepository.getAvailableLanguages(),
                    availableUnits = settingsRepository.getAvailableUnits()
                )
            }
        }
    }
}