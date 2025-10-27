package com.andrii_a.shakeit.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andrii_a.shakeit.data.remote.service.IngredientService
import com.andrii_a.shakeit.data.remote.source.IngredientsPagingSource
import com.andrii_a.shakeit.data.util.PAGE_SIZE
import com.andrii_a.shakeit.domain.model.Ingredient
import com.andrii_a.shakeit.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl(private val ingredientService: IngredientService) : IngredientRepository {

    override fun getIngredients(isAlcoholic: Boolean?): Flow<PagingData<Ingredient>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                IngredientsPagingSource(
                    ingredientService = ingredientService,
                    isAlcoholic = isAlcoholic.toString(),
                )
            }
        ).flow
    }
}