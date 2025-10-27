package com.andrii_a.shakeit.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            baseNetworkModule,
            mainDatabaseModule,
            platformDatabaseModule,
            platformDataStoreModule,
            cocktailModule,
            ingredientModule,
            savedCocktailsModule,
            searchHistoryModule,
            localPreferencesModule,
            settingsModule
        )
    }
}