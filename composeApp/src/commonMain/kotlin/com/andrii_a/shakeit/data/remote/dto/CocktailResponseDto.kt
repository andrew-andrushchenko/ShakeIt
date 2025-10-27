package com.andrii_a.shakeit.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CocktailResponseDto(
    val data: CocktailDto
)
