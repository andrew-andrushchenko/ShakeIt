package com.andrii_a.shakeit.domain.model

enum class CocktailGlass(val glassName: String) {
    HighballGlass("Highball glass"),
    OldFashionedGlass("Old-fashioned glass"),
    Cocktail("Cocktail glass"),
    CopperMug("Copper Mug"),
    WhiskeyGlass("Whiskey Glass"),
    CollinsGlass("Collins glass"),
    PousseCafeGlass("Pousse cafe glass"),
    ChampagneFlute("Champagne flute"),
    WhiskeySourGlass("Whiskey sour glass"),
    BrandySnifter("Brandy snifter"),
    WhiteWineGlass("White wine glass"),
    NickAndNoraGlass("Nick and Nora Glass"),
    HurricaneGlass("Hurricane glass"),
    CoffeeMug("Coffee mug"),
    ShotGlass("Shot glass"),
    Jar("Jar"),
    IrishCoffeeCup("Irish coffee cup"),
    PunchBowl("Punch bowl"),
    Pitcher("Pitcher"),
    PintGlass("Pint glass"),
    CordialGlass("Cordial glass"),
    BeerMug("Beer mug"),
    MargaritaCoupetteGlass("Margarita/Coupette glass"),
    BeerPilsner("Beer pilsner"),
    BeerGlass("Beer Glass"),
    ParfaitGlass("Parfait glass"),
    WineGlass("Wine Glass"),
    MasonJar("Mason jar"),
    MargaritaGlass("Margarita glass"),
    MartiniGlass("Martini Glass"),
    BalloonGlass("Balloon Glass"),
    CoupeGlass("Coupe Glass");

    companion object {
        private val map = CocktailGlass.entries.associateBy { it.glassName }
        infix fun from(value: String) = map[value] ?: Cocktail
    }
}