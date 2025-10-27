package com.andrii_a.shakeit.domain.repository

import androidx.paging.PagingData
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.CocktailCategory
import com.andrii_a.shakeit.domain.model.CocktailGlass
import com.andrii_a.shakeit.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    fun getCocktails(
        name: String? = null,
        hasAlcohol: Boolean? = null,
        category: CocktailCategory? = null,
        glass: CocktailGlass? = null,
        ingredientId: Int? = null,
        ingredients: Boolean
    ): Flow<PagingData<Cocktail>>

    fun getCocktail(id: Int): Flow<Resource<Cocktail>>
}