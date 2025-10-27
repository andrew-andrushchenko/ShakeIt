package com.andrii_a.shakeit.presentation.favourites

import androidx.compose.runtime.Stable
import com.andrii_a.shakeit.domain.model.SavedCocktail
import com.andrii_a.shakeit.domain.model.SortOrder

@Stable
data class FavouritesUiState(
    val cocktails: List<SavedCocktail> = emptyList(),
    val sortOrder: SortOrder = SortOrder.BY_DATE_SAVED,
    val isOrderMenuExpanded: Boolean = false
)
