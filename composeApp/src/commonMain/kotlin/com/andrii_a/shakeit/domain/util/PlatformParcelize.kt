package com.andrii_a.shakeit.domain.util

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class PlatformParcelize()

expect interface PlatformParcelable