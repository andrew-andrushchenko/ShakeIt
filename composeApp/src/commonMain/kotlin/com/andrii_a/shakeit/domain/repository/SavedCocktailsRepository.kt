package com.andrii_a.shakeit.domain.repository

import com.andrii_a.shakeit.domain.model.SavedCocktail
import com.andrii_a.shakeit.domain.model.SortOrder
import kotlinx.coroutines.flow.Flow

interface SavedCocktailsRepository {

    fun getSavedCocktails(sortOrder: SortOrder = SortOrder.BY_DATE_SAVED): Flow<List<SavedCocktail>>

    suspend fun getSavedCocktailByName(name: String): SavedCocktail?

    suspend fun save(cocktail: SavedCocktail)

    suspend fun unsave(cocktail: SavedCocktail)

    suspend fun unsaveAll()

}