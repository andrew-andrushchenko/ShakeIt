package com.andrii_a.shakeit

import android.app.Application
import com.andrii_a.shakeit.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ShakeItApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ShakeItApplication)
            androidLogger()
        }
    }

}