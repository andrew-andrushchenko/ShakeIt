package com.andrii_a.shakeit.data.util

import com.andrii_a.shakeit.data.local.db.entity.SavedCocktailEntity
import com.andrii_a.shakeit.data.local.db.entity.SearchHistoryEntity
import com.andrii_a.shakeit.data.remote.dto.CocktailDto
import com.andrii_a.shakeit.data.remote.dto.IngredientDto
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.CocktailCategory
import com.andrii_a.shakeit.domain.model.CocktailGlass
import com.andrii_a.shakeit.domain.model.Ingredient
import com.andrii_a.shakeit.domain.model.SavedCocktail
import com.andrii_a.shakeit.domain.model.SearchHistoryItem

fun CocktailDto.toCocktail(): Cocktail {
    return Cocktail(
        id = id,
        name = name,
        category = CocktailCategory from category,
        glass = CocktailGlass from glass,
        instructions = instructions,
        imageUrl = imageUrl,
        isAlcoholic = alcoholic,
        ingredients = ingredients.map { it.toIngredient() }
    )
}

fun IngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        id = id,
        name = name,
        description = description.orEmpty(),
        isAlcohol = alcohol,
        type = type.orEmpty(),
        percentage = percentage,
        imageUrl = imageUrl.orEmpty()
    )
}

fun SavedCocktailEntity.toSavedCocktail(): SavedCocktail {
    return SavedCocktail(
        id = id,
        name = name,
        imageUrl = imageUrl,
        cocktailId = cocktailId,
        timestampMillis = timestampMillis
    )
}

fun SavedCocktail.asEntity(): SavedCocktailEntity {
    return SavedCocktailEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        cocktailId = cocktailId,
        timestampMillis = timestampMillis
    )
}

fun SearchHistoryEntity.toSearchHistoryItem(): SearchHistoryItem {
    return SearchHistoryItem(
        id = id,
        title = title,
        timestampMillis = timestampMillis
    )
}

fun SearchHistoryItem.toSearchHistoryEntity(): SearchHistoryEntity {
    return SearchHistoryEntity(
        id = id,
        title = title,
        timestampMillis = timestampMillis
    )
}