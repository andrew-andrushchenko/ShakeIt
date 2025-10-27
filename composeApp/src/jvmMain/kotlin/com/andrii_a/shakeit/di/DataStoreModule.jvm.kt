package com.andrii_a.shakeit.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module
import java.io.File

actual val platformDataStoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = {
                val file = File(System.getProperty("java.io.tmpdir"), dataStoreFileName)
                file.absolutePath
            }
        )
    }
}