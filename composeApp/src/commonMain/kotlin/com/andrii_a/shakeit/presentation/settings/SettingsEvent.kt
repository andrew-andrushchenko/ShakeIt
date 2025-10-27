package com.andrii_a.shakeit.presentation.settings

import com.andrii_a.shakeit.domain.preferences.AppLanguage
import com.andrii_a.shakeit.domain.preferences.AppTheme

sealed interface SettingsEvent {

    data class UpdateAppTheme(val appTheme: AppTheme) : SettingsEvent

    data class UpdateAppLanguage(val appLanguage: AppLanguage) : SettingsEvent

}