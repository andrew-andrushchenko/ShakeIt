package com.andrii_a.shakeit.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrii_a.shakeit.data.local.db.ShakeItDatabase
import org.koin.dsl.module
import java.io.File

actual val platformDatabaseModule = module {
    single<RoomDatabase.Builder<ShakeItDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "shake_it.db")

        Room.databaseBuilder<ShakeItDatabase>(
            name = dbFile.absolutePath,
        )
    }
}