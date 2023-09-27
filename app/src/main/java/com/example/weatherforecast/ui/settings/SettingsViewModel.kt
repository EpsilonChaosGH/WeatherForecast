package com.example.weatherforecast.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SettingsRepository
import com.example.data.entity.SettingsState
import com.example.weatherforecast.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val state: StateFlow<SettingsState> = settingsRepository.getSettingsFlow().stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = SettingsState()
    )

    fun setLanguageIndex(languageIndex: Int) {
        viewModelScope.launch {
            settingsRepository.setLanguageIndex(languageIndex)
        }
    }

    fun setUnitsIndex(unitsIndex: Int) {
        viewModelScope.launch {
            settingsRepository.setUnitsIndex(unitsIndex)
        }
    }
}