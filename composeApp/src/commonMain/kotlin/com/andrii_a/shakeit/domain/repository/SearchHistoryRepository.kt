package com.andrii_a.shakeit.domain.repository

import com.andrii_a.shakeit.domain.model.SearchHistoryItem
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getSearchHistory(): Flow<List<SearchHistoryItem>>

    suspend fun getSearchHistoryItemByTitle(title: String): SearchHistoryItem?

    suspend fun addItem(item: SearchHistoryItem)

    suspend fun updateItem(item: SearchHistoryItem)

    suspend fun removeItem(item: SearchHistoryItem)

    suspend fun clearSearchHistory()

}