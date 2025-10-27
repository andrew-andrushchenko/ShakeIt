package com.andrii_a.shakeit.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.andrii_a.shakeit.data.local.db.ShakeItDatabase
import com.andrii_a.shakeit.data.local.db.dao.SavedCocktailsDao
import com.andrii_a.shakeit.data.local.db.dao.SearchHistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDatabaseModule: Module

val mainDatabaseModule = module {
    single<ShakeItDatabase> { getRoomDatabase(get()) }

    factory<SavedCocktailsDao> { getSavedCocktailsDao(get()) }

    factory<SearchHistoryDao> { getSearchHistoryDao(get()) }
}

private fun getRoomDatabase(builder: RoomDatabase.Builder<ShakeItDatabase>): ShakeItDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(dropAllTables = false)
        .build()
}

private fun getSavedCocktailsDao(database: ShakeItDatabase): SavedCocktailsDao {
    return database.savedCocktailsDao()
}

private fun getSearchHistoryDao(database: ShakeItDatabase): SearchHistoryDao {
    return database.searchHistoryDao()
}