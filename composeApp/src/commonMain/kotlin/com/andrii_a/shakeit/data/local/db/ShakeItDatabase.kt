package com.andrii_a.shakeit.data.local.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.andrii_a.shakeit.data.local.db.dao.SavedCocktailsDao
import com.andrii_a.shakeit.data.local.db.dao.SearchHistoryDao
import com.andrii_a.shakeit.data.local.db.entity.SavedCocktailEntity
import com.andrii_a.shakeit.data.local.db.entity.SearchHistoryEntity

@Database(
    entities = [SavedCocktailEntity::class, SearchHistoryEntity::class],
    version = 3
)
@ConstructedBy(ShakeItDatabaseConstructor::class)
abstract class ShakeItDatabase : RoomDatabase() {
    abstract fun savedCocktailsDao(): SavedCocktailsDao

    abstract fun searchHistoryDao(): SearchHistoryDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object ShakeItDatabaseConstructor : RoomDatabaseConstructor<ShakeItDatabase> {
    override fun initialize(): ShakeItDatabase
}