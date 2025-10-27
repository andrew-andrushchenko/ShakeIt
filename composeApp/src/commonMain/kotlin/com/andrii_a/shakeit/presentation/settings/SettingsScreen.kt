package com.andrii_a.shakeit.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.andrii_a.shakeit.domain.preferences.AppLanguage
import com.andrii_a.shakeit.domain.preferences.AppTheme
import com.andrii_a.shakeit.presentation.util.titleRes
import org.jetbrains.compose.resources.stringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.app_theme
import shakeit.composeapp.generated.resources.language
import shakeit.composeapp.generated.resources.settings
import shakeit.composeapp.generated.resources.settings_appearance
import shakeit.composeapp.generated.resources.settings_region_and_language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onEvent: (SettingsEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(Res.string.settings))
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            SettingsGroup(
                name = stringResource(Res.string.settings_appearance),
                modifier = Modifier.widthIn(max = 500.dp)
            ) {
                SettingsItem(
                    title = stringResource(Res.string.app_theme),
                    selectedValue = stringResource(state.appTheme.titleRes),
                    selectionOptions = AppTheme.entries.map { stringResource(it.titleRes) },
                    selectedItemPositionOrdinal = state.appTheme.ordinal,
                    onChangeParameter = { selectedAppThemeOrdinal ->
                        onEvent(
                            SettingsEvent.UpdateAppTheme(
                                AppTheme.entries[selectedAppThemeOrdinal]
                            )
                        )
                    }
                )
            }

            SettingsGroup(
                name = stringResource(Res.string.settings_region_and_language),
                modifier = Modifier.widthIn(max = 500.dp)
            ) {
                SettingsItem(
                    title = stringResource(Res.string.language),
                    selectedValue = stringResource(state.appLanguage.titleRes),
                    selectionOptions = AppLanguage.entries.map { stringResource(it.titleRes) },
                    selectedItemPositionOrdinal = state.appLanguage.ordinal,
                    onChangeParameter = { selectedAppLanguageOrdinal ->
                        onEvent(
                            SettingsEvent.UpdateAppLanguage(
                                AppLanguage.entries[selectedAppLanguageOrdinal]
                            )
                        )
                    }
                )
            }
        }
    }
}