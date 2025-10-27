package com.andrii_a.shakeit.data.util

import com.andrii_a.shakeit.BuildConfig

const val BASE_URL = BuildConfig.BASE_URL

enum class Endpoints(val url: String) {
    Cocktails("$BASE_URL/cocktails"),
    Ingredients("$BASE_URL/ingredients")
}

const val INITIAL_PAGE_INDEX = 1
const val PAGE_SIZE = 15