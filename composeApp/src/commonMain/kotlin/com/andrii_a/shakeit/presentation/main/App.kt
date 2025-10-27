package com.andrii_a.shakeit.presentation.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.andrii_a.shakeit.presentation.navigation.NavigationDestination
import com.andrii_a.shakeit.presentation.navigation.NavigationRoot
import com.andrii_a.shakeit.presentation.navigation.Screen
import com.andrii_a.shakeit.presentation.theme.ShakeItTheme
import com.andrii_a.shakeit.presentation.util.currentScreenClassName
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.navigation_icon

@Composable
@Preview
fun App(darkTheme: Boolean = isSystemInDarkTheme()) {
    ShakeItTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()
        var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

        val adaptiveInfo = currentWindowAdaptiveInfo()
        val navigationSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                NavigationDestination.entries.forEachIndexed { index, destination ->
                    item(
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTabIndex)
                                    destination.iconSelected
                                else
                                    destination.iconUnselected,
                                contentDescription = stringResource(resource = Res.string.navigation_icon)
                            )
                        },
                        label = { Text(text = stringResource(resource = destination.titleRes)) },
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                            navController.navigate(destination.screen) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true

                            }
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            },
            layoutType = if (navController.currentScreenClassName == Screen.CocktailDescription::class.simpleName)
                NavigationSuiteType.None
            else
                navigationSuiteType
        ) {
            NavigationRoot(navController)
        }
    }
}