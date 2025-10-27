package com.andrii_a.shakeit.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientsResponseDto(
    val meta: ResponseMetadataDto,
    val data: List<IngredientDto>
)
