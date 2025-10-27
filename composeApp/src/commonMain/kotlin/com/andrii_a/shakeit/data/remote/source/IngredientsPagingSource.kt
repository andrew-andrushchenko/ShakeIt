package com.andrii_a.shakeit.data.remote.source

import com.andrii_a.shakeit.data.remote.base.BasePagingSource
import com.andrii_a.shakeit.data.remote.service.IngredientService
import com.andrii_a.shakeit.data.util.INITIAL_PAGE_INDEX
import com.andrii_a.shakeit.data.util.PAGE_SIZE
import com.andrii_a.shakeit.data.util.toIngredient
import com.andrii_a.shakeit.domain.model.Ingredient
import com.andrii_a.shakeit.domain.util.Resource
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class IngredientsPagingSource(
    private val ingredientService: IngredientService,
    private val isAlcoholic: String?
) : BasePagingSource<Ingredient>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Ingredient> {
        val pageKey = params.key ?: INITIAL_PAGE_INDEX

        return try {
            val result = ingredientService.getIngredients(
                page = pageKey,
                perPage = PAGE_SIZE,
                isAlcoholic = isAlcoholic
            )

            val ingredients: List<Ingredient> = when (result) {
                is Resource.Empty, Resource.Loading -> emptyList()
                is Resource.Error -> throw result.asException()
                is Resource.Success -> result.value.data.map { it.toIngredient() }
            }

            LoadResult.Page(
                data = ingredients,
                prevKey = if (pageKey == INITIAL_PAGE_INDEX) null else pageKey - 1,
                nextKey = if (ingredients.isEmpty()) null else pageKey + 1
            )
        } catch (exception: Exception) {
            coroutineContext.ensureActive()
            LoadResult.Error(exception)
        }
    }
}