package com.andrii_a.shakeit.di

import com.andrii_a.shakeit.data.local.db.repository.SavedCocktailsRepositoryImpl
import com.andrii_a.shakeit.domain.repository.SavedCocktailsRepository
import com.andrii_a.shakeit.presentation.favourites.FavouritesViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val savedCocktailsModule = module {
    singleOf(::SavedCocktailsRepositoryImpl) { bind<SavedCocktailsRepository>() }

    viewModelOf(::FavouritesViewModel)
}