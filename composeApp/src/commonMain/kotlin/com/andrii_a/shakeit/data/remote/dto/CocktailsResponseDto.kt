package com.andrii_a.shakeit.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CocktailsResponseDto(
    val meta: ResponseMetadataDto,
    val data: List<CocktailDto>
)
