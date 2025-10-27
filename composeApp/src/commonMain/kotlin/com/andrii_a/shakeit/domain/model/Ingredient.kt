package com.andrii_a.shakeit.domain.model

import com.andrii_a.shakeit.domain.util.PlatformParcelable
import com.andrii_a.shakeit.domain.util.PlatformParcelize

@PlatformParcelize
data class Ingredient(
    val id: Int,
    val name: String,
    val description: String,
    val isAlcohol: Boolean,
    val type: String,
    val percentage: Int?,
    val imageUrl: String
) : PlatformParcelable