package com.andrii_a.shakeit.di

import com.andrii_a.shakeit.data.local.db.repository.SearchHistoryRepositoryImpl
import com.andrii_a.shakeit.domain.repository.SearchHistoryRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val searchHistoryModule = module {
    singleOf(::SearchHistoryRepositoryImpl) { bind<SearchHistoryRepository>() }
}