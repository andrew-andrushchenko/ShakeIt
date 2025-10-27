package com.andrii_a.shakeit.di

import com.andrii_a.shakeit.data.local.preferences.LocalPreferencesRepositoryImpl
import com.andrii_a.shakeit.domain.preferences.LocalPreferencesRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localPreferencesModule = module {
    singleOf(::LocalPreferencesRepositoryImpl) { bind<LocalPreferencesRepository>() }
}