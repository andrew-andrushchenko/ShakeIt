package com.andrii_a.shakeit.presentation.cocktails

import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.Ingredient
import com.andrii_a.shakeit.domain.model.SearchHistoryItem
import com.andrii_a.shakeit.presentation.cocktails.components.CocktailSearchOptions

sealed interface CocktailsEvent {
    data object RequestCocktails : CocktailsEvent

    data class SelectCocktail(val cocktail: Cocktail) : CocktailsEvent

    data class SelectCocktailFromFavourites(val cocktailId: Int) : CocktailsEvent

    data object CleanCocktailSelection : CocktailsEvent

    data class SaveCocktail(val cocktail: Cocktail) : CocktailsEvent

    data class UnsaveCocktail(val cocktail: Cocktail) : CocktailsEvent

    data class ShowIngredientDetailDialog(val ingredient: Ingredient) : CocktailsEvent

    data object DismissIngredientDetailDialog : CocktailsEvent

    data class UpdateSearchOptions(val options: CocktailSearchOptions) : CocktailsEvent

    data class RemoveSearchHistoryItem(val item: SearchHistoryItem) : CocktailsEvent

    data object ClearSearchHistory : CocktailsEvent
}