package com.andrii_a.shakeit.presentation.favourites

import com.andrii_a.shakeit.domain.model.SavedCocktail
import com.andrii_a.shakeit.domain.model.SortOrder

sealed interface FavouritesEvent {

    data class ChangeSortOrder(val sortOrder: SortOrder) : FavouritesEvent

    data class SelectCocktail(val cocktail: SavedCocktail) : FavouritesEvent

    data class UnsaveCocktail(val cocktail: SavedCocktail) : FavouritesEvent

    data object UnsaveAllCocktails : FavouritesEvent

    data class ToggleListOrderMenu(val isExpanded: Boolean) : FavouritesEvent

}