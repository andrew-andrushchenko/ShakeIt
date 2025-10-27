package com.andrii_a.shakeit.di

import com.andrii_a.shakeit.data.remote.repository.IngredientRepositoryImpl
import com.andrii_a.shakeit.data.remote.service.IngredientService
import com.andrii_a.shakeit.data.remote.service.IngredientServiceImpl
import com.andrii_a.shakeit.domain.repository.IngredientRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val ingredientModule = module {
    singleOf(::IngredientServiceImpl) { bind<IngredientService>() }
    singleOf(::IngredientRepositoryImpl) { bind<IngredientRepository>() }
}