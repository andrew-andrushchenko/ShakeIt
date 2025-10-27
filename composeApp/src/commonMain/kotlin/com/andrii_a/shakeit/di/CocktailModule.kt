package com.andrii_a.shakeit.di

import com.andrii_a.shakeit.data.remote.repository.CocktailRepositoryImpl
import com.andrii_a.shakeit.data.remote.service.CocktailService
import com.andrii_a.shakeit.data.remote.service.CocktailServiceImpl
import com.andrii_a.shakeit.domain.repository.CocktailRepository
import com.andrii_a.shakeit.presentation.cocktails.CocktailsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cocktailModule = module {
    singleOf(::CocktailServiceImpl) { bind<CocktailService>() }
    singleOf(::CocktailRepositoryImpl) { bind<CocktailRepository>() }

    viewModelOf(::CocktailsViewModel)
}