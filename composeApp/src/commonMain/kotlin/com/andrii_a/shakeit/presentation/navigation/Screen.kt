package com.andrii_a.shakeit.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data class Cocktails(val selectedCocktailId: Int? = null) : Screen

    @Serializable
    data class CocktailDescription(val cocktailId: Int) : Screen

    @Serializable
    data object Favourites : Screen

    @Serializable
    data object Settings : Screen
}