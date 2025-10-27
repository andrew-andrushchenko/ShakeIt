package com.andrii_a.shakeit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.andrii_a.shakeit.di.initKoin
import com.andrii_a.shakeit.domain.preferences.LocalPreferencesRepository
import com.andrii_a.shakeit.presentation.main.App
import com.andrii_a.shakeit.presentation.util.shouldUseDarkTheme
import org.koin.compose.koinInject

fun main() = application {
    initKoin()
    val localPreferencesRepository: LocalPreferencesRepository = koinInject()

    Window(
        onCloseRequest = ::exitApplication,
        title = "ShakeIt",
    ) {
        App(darkTheme = localPreferencesRepository.shouldUseDarkTheme)
    }
}