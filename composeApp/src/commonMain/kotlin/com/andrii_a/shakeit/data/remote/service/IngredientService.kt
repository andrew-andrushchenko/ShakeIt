package com.andrii_a.shakeit.data.remote.service

import com.andrii_a.shakeit.data.remote.dto.IngredientsResponseDto
import com.andrii_a.shakeit.domain.util.Resource

interface IngredientService {
    suspend fun getIngredients(
        page: Int,
        perPage: Int,
        isAlcoholic: String?
    ): Resource<IngredientsResponseDto>

}