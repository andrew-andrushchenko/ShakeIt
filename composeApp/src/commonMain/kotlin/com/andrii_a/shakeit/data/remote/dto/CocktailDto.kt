package com.andrii_a.shakeit.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CocktailDto(
    val id: Int,
    val name: String,
    val category: String,
    val glass: String,
    val instructions: String,
    val imageUrl: String,
    val alcoholic: Boolean,
    val ingredients: List<IngredientDto>
)
