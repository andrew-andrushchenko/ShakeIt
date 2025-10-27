package com.andrii_a.shakeit.domain.model

import com.andrii_a.shakeit.domain.util.PlatformParcelable
import com.andrii_a.shakeit.domain.util.PlatformParcelize

@PlatformParcelize
data class SavedCocktail(
    val id: Int = 0,
    val name: String,
    val imageUrl: String,
    val cocktailId: Int,
    val timestampMillis: Long
) : PlatformParcelable
