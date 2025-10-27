package com.andrii_a.shakeit.presentation.favourites

sealed interface FavouritesNavigationEvent {

    data class NavigateToCocktailDetail(val cocktailId: Int) : FavouritesNavigationEvent
}