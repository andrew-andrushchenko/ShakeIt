package com.andrii_a.shakeit.domain.repository

import androidx.paging.PagingData
import com.andrii_a.shakeit.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getIngredients(isAlcoholic: Boolean? = null): Flow<PagingData<Ingredient>>
}