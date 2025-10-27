package com.andrii_a.shakeit.presentation.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrii_a.shakeit.domain.preferences.AppTheme
import com.andrii_a.shakeit.domain.preferences.LocalPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val LocalPreferencesRepository.shouldUseDarkTheme: Boolean
    @Composable
    get() {
        val appTheme by this.appTheme.collectAsStateWithLifecycle(
            initialValue = runBlocking { this@shouldUseDarkTheme.appTheme.first() }
        )

        return when (appTheme) {
            AppTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme()
            AppTheme.LIGHT -> false
            AppTheme.DARK -> true
        }
    }