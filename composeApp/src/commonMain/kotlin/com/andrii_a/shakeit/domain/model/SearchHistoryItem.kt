package com.andrii_a.shakeit.domain.model

data class SearchHistoryItem(
    val id: Int = 0,
    val title: String,
    val timestampMillis: Long
)
