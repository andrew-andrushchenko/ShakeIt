package com.andrii_a.shakeit.data.remote.service

import com.andrii_a.shakeit.data.remote.dto.CocktailResponseDto
import com.andrii_a.shakeit.data.remote.dto.CocktailsResponseDto
import com.andrii_a.shakeit.domain.util.Resource

interface CocktailService {
    suspend fun getCocktails(
        page: Int,
        perPage: Int,
        name: String,
        category: String,
        hasAlcohol: String,
        glass: String,
        ingredientId: Int?,
        ingredients: Boolean
    ): Resource<CocktailsResponseDto>

    suspend fun getCocktail(id: Int): Resource<CocktailResponseDto>
}