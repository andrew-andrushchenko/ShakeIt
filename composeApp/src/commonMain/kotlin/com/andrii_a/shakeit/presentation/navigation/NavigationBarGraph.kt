package com.andrii_a.shakeit.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.andrii_a.shakeit.presentation.cocktails.cocktailsRoute
import com.andrii_a.shakeit.presentation.favourites.favouritesRoute
import com.andrii_a.shakeit.presentation.settings.settingsRoute
import kotlinx.serialization.Serializable

@Serializable
object NavigationBarGraph

fun NavGraphBuilder.navigationBarGraph(navController: NavController) {
    navigation<NavigationBarGraph>(startDestination = Screen.Cocktails()) {
        cocktailsRoute(navController)
        favouritesRoute(navController)
        settingsRoute()
    }
}