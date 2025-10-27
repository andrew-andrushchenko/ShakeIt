package com.andrii_a.shakeit.presentation.settings

import androidx.compose.runtime.Stable
import com.andrii_a.shakeit.domain.preferences.AppLanguage
import com.andrii_a.shakeit.domain.preferences.AppTheme

@Stable
data class SettingsUiState(
    val appTheme: AppTheme = AppTheme.SYSTEM_DEFAULT,
    val appLanguage: AppLanguage = AppLanguage.ENGLISH
)
