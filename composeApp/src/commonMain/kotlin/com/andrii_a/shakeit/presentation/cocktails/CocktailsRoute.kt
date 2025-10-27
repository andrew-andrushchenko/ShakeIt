package com.andrii_a.shakeit.presentation.cocktails

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.shakeit.presentation.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.cocktailsRoute(navController: NavController) {
    composable<Screen.Cocktails> {
        val viewModel = koinViewModel<CocktailsViewModel>()

        val state by viewModel.state.collectAsStateWithLifecycle()

        CocktailsScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}