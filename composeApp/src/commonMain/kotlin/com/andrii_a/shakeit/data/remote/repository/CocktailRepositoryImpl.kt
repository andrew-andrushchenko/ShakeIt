package com.andrii_a.shakeit.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andrii_a.shakeit.data.remote.service.CocktailService
import com.andrii_a.shakeit.data.remote.source.CocktailsPagingSource
import com.andrii_a.shakeit.data.util.PAGE_SIZE
import com.andrii_a.shakeit.data.util.toCocktail
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.CocktailCategory
import com.andrii_a.shakeit.domain.model.CocktailGlass
import com.andrii_a.shakeit.domain.repository.CocktailRepository
import com.andrii_a.shakeit.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CocktailRepositoryImpl(private val cocktailService: CocktailService) : CocktailRepository {

    override fun getCocktails(
        name: String?,
        hasAlcohol: Boolean?,
        category: CocktailCategory?,
        glass: CocktailGlass?,
        ingredientId: Int?,
        ingredients: Boolean
    ): Flow<PagingData<Cocktail>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CocktailsPagingSource(
                    cocktailService = cocktailService,
                    name = name.orEmpty(),
                    category = category?.categoryName.orEmpty(),
                    hasAlcohol = hasAlcohol?.toString().orEmpty(),
                    glass = glass?.glassName.orEmpty(),
                    ingredientId = ingredientId,
                    ingredients = ingredients
                )
            }
        ).flow
    }

    override fun getCocktail(id: Int): Flow<Resource<Cocktail>> {
        return flow {
            emit(Resource.Loading)

            when (val result = cocktailService.getCocktail(id)) {
                is Resource.Error -> emit(result)
                is Resource.Success -> emit(Resource.Success(result.value.data.toCocktail()))
                else -> Unit
            }
        }
    }
}