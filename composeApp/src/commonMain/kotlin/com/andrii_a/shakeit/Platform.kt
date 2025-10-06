package com.andrii_a.shakeit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform