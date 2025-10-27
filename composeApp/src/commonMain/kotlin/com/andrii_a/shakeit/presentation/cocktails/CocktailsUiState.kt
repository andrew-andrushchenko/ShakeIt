package com.andrii_a.shakeit.presentation.cocktails

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.Ingredient
import com.andrii_a.shakeit.domain.model.SearchHistoryItem
import com.andrii_a.shakeit.presentation.cocktails.components.CocktailSearchOptions
import com.andrii_a.shakeit.presentation.util.emptyPagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
data class CocktailsUiState(
    private val cocktailsPagingData: PagingData<Cocktail> = emptyPagingData(),
    val selectedCocktail: Cocktail? = null,
    val selectedIngredient: Ingredient? = null,
    val searchOptions: CocktailSearchOptions = CocktailSearchOptions(),
    val isSelectedCocktailInFavouritesList: Boolean = false,
    val searchHistory: List<SearchHistoryItem> = emptyList(),
    val error: String? = null
) {
    private val _cocktails: MutableStateFlow<PagingData<Cocktail>> = MutableStateFlow(emptyPagingData())
    val cocktails: StateFlow<PagingData<Cocktail>> = _cocktails.asStateFlow()

    init {
        _cocktails.update { cocktailsPagingData }
    }
}
