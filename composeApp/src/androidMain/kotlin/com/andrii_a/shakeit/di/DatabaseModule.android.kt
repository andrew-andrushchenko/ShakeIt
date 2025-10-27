package com.andrii_a.shakeit.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrii_a.shakeit.data.local.db.ShakeItDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformDatabaseModule = module {
    single<RoomDatabase.Builder<ShakeItDatabase>> {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath("shake_it.db")

        Room.databaseBuilder<ShakeItDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}