package com.andrii_a.shakeit.data.local.db.repository

import com.andrii_a.shakeit.data.local.db.dao.SearchHistoryDao
import com.andrii_a.shakeit.data.util.toSearchHistoryEntity
import com.andrii_a.shakeit.data.util.toSearchHistoryItem
import com.andrii_a.shakeit.domain.model.SearchHistoryItem
import com.andrii_a.shakeit.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class SearchHistoryRepositoryImpl(private val dao: SearchHistoryDao) : SearchHistoryRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSearchHistory(): Flow<List<SearchHistoryItem>> {
        return dao.getSearchHistory().flatMapLatest { entityList ->
            flow {
                val searchHistory = entityList.map { it.toSearchHistoryItem() }
                emit(searchHistory)
            }
        }
    }

    override suspend fun getSearchHistoryItemByTitle(title: String): SearchHistoryItem? {
        return dao.getSearchHistoryEntityByTitle(title)?.toSearchHistoryItem()
    }

    override suspend fun addItem(item: SearchHistoryItem) {
        dao.insert(item.toSearchHistoryEntity())
    }

    override suspend fun updateItem(item: SearchHistoryItem) {
        dao.update(item.toSearchHistoryEntity())
    }

    override suspend fun removeItem(item: SearchHistoryItem) {
        dao.delete(item.toSearchHistoryEntity())
    }

    override suspend fun clearSearchHistory() {
        dao.deleteAll()
    }
}