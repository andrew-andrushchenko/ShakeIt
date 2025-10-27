package com.andrii_a.shakeit.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.andrii_a.shakeit.domain.preferences.LocalPreferencesRepository
import com.andrii_a.shakeit.presentation.util.shouldUseDarkTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val localPreferencesRepository by inject<LocalPreferencesRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(darkTheme = localPreferencesRepository.shouldUseDarkTheme)
        }
    }
}