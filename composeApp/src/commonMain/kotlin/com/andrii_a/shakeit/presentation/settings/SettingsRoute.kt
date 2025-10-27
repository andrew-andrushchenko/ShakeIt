package com.andrii_a.shakeit.presentation.settings

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.shakeit.presentation.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.settingsRoute() {
    composable<Screen.Settings> {
        val viewModel: SettingsViewModel = koinViewModel()

        val state by viewModel.state.collectAsStateWithLifecycle()

        SettingsScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}