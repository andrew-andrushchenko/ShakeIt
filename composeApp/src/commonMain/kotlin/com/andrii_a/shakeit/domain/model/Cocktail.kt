package com.andrii_a.shakeit.domain.model

import com.andrii_a.shakeit.domain.util.PlatformParcelable
import com.andrii_a.shakeit.domain.util.PlatformParcelize

@PlatformParcelize
data class Cocktail(
    val id: Int,
    val name: String,
    val category: CocktailCategory,
    val glass: CocktailGlass,
    val instructions: String,
    val imageUrl: String,
    val isAlcoholic: Boolean,
    val ingredients: List<Ingredient>
) : PlatformParcelable