package com.andrii_a.shakeit.presentation.favourites

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.shakeit.presentation.navigation.Screen
import com.andrii_a.shakeit.presentation.util.CollectAsOneTimeEvents
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.favouritesRoute(navController: NavController) {
    composable<Screen.Favourites> {
        val viewModel: FavouritesViewModel = koinViewModel()

        val state by viewModel.state.collectAsStateWithLifecycle()

        viewModel.navigationEventFlow.CollectAsOneTimeEvents { event ->
            when (event) {
                is FavouritesNavigationEvent.NavigateToCocktailDetail -> {
                    navController.navigate(Screen.Cocktails(event.cocktailId))
                }
            }
        }

        FavouritesScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}