package com.andrii_a.shakeit.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.andrii_a.shakeit.data.local.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history_table ORDER BY timestampMillis DESC")
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM search_history_table WHERE title = :title LIMIT 1")
    suspend fun getSearchHistoryEntityByTitle(title: String): SearchHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistoryEntity: SearchHistoryEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(searchHistoryEntity: SearchHistoryEntity)

    @Delete
    suspend fun delete(searchHistoryEntity: SearchHistoryEntity)

    @Query("DELETE FROM search_history_table")
    suspend fun deleteAll()
}