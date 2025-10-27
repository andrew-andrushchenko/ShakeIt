package com.andrii_a.shakeit.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformDataStoreModule = module {
    single<DataStore<Preferences>> {
        val appContext = androidContext().applicationContext

        createDataStore(
            producePath = { appContext.filesDir.resolve(dataStoreFileName).absolutePath }
        )
    }
}