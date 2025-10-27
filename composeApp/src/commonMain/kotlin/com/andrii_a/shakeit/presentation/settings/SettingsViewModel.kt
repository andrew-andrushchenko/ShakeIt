package com.andrii_a.shakeit.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrii_a.shakeit.domain.preferences.LocalPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val localPreferencesRepository: LocalPreferencesRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val state = combine(
        localPreferencesRepository.appTheme,
        localPreferencesRepository.appLanguage,
        _state
    ) { appTheme, appLanguage, state ->
        state.copy(
            appTheme = appTheme,
            appLanguage = appLanguage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.UpdateAppTheme -> {
                viewModelScope.launch {
                    localPreferencesRepository.updateAppTheme(event.appTheme)
                }
            }
            is SettingsEvent.UpdateAppLanguage -> {
                viewModelScope.launch {
                    localPreferencesRepository.updateAppLanguage(event.appLanguage)
                }
            }
        }
    }
}