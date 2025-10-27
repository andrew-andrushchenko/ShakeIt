package com.andrii_a.shakeit.domain.model

enum class CocktailCategory(val categoryName: String) {
    Cocktail("Cocktail"),
    OrdinaryDrink("Ordinary Drink"),
    PunchPartyDrink("Punch / Party Drink"),
    Shake("Shake"),
    OtherUnknown("Other / Unknown"),
    Cocoa("Cocoa"),
    Shot("Shot"),
    CoffeeTea("Coffee / Tea"),
    HomemadeLiqueur("Homemade Liqueur"),
    SoftDrink("Soft Drink");

    companion object {
        private val map = CocktailCategory.entries.associateBy { it.categoryName }
        infix fun from(value: String) = map[value] ?: OtherUnknown
    }
}