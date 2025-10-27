package com.andrii_a.shakeit.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val id: Int,
    val name: String,
    val description: String?,
    val alcohol: Boolean,
    val type: String?,
    val percentage: Int?,
    val imageUrl: String?
)
