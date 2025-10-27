package com.andrii_a.shakeit.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val baseNetworkModule = module {
    single<HttpClient> { createHttpClient(OkHttp.create()) }
}