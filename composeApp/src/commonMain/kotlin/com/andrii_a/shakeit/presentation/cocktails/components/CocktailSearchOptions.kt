package com.andrii_a.shakeit.presentation.cocktails.components

import com.andrii_a.shakeit.domain.model.CocktailCategory
import com.andrii_a.shakeit.domain.model.CocktailGlass

data class CocktailSearchOptions(
    val name: String? = null,
    val hasAlcohol: Boolean? = null,
    val category: CocktailCategory? = null,
    val glass: CocktailGlass? = null
)