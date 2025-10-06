package com.andrii_a.shakeit

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}