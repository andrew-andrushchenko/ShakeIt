package com.andrii_a.shakeit.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMetadataDto(
    val total: Int,
    val currentPage: Int,
)
