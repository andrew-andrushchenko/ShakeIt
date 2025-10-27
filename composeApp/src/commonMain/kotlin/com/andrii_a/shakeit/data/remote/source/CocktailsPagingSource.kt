package com.andrii_a.shakeit.data.remote.source

import com.andrii_a.shakeit.data.remote.base.BasePagingSource
import com.andrii_a.shakeit.data.remote.service.CocktailService
import com.andrii_a.shakeit.data.util.INITIAL_PAGE_INDEX
import com.andrii_a.shakeit.data.util.PAGE_SIZE
import com.andrii_a.shakeit.data.util.toCocktail
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.util.Resource
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class CocktailsPagingSource(
    private val cocktailService: CocktailService,
    private val name: String,
    private val category: String,
    private val hasAlcohol: String,
    private val glass: String,
    private val ingredientId: Int?,
    private val ingredients: Boolean
) : BasePagingSource<Cocktail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cocktail> {
        val pageKey = params.key ?: INITIAL_PAGE_INDEX

        return try {
            val result = cocktailService.getCocktails(
                page = pageKey,
                perPage = PAGE_SIZE,
                name = name,
                category = category,
                hasAlcohol = hasAlcohol,
                glass = glass,
                ingredientId = ingredientId,
                ingredients = ingredients
            )

            val cocktails: List<Cocktail> = when (result) {
                is Resource.Empty, Resource.Loading -> emptyList()
                is Resource.Error -> throw result.asException()
                is Resource.Success -> result.value.data.map { it.toCocktail() }
            }

            LoadResult.Page(
                data = cocktails,
                prevKey = if (pageKey == INITIAL_PAGE_INDEX) null else pageKey - 1,
                nextKey = if (cocktails.isEmpty()) null else pageKey + 1
            )
        } catch (exception: Exception) {
            coroutineContext.ensureActive()
            LoadResult.Error(exception)
        }
    }
}