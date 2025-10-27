package com.andrii_a.shakeit.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrii_a.shakeit.data.local.db.entity.SavedCocktailEntity
import com.andrii_a.shakeit.domain.model.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedCocktailsDao {

    fun getSavedCocktails(order: SortOrder): Flow<List<SavedCocktailEntity>> {
        return when (order) {
            SortOrder.BY_NAME -> {
                getSavedCocktailsOrderedByName()
            }
            SortOrder.BY_DATE_SAVED -> {
                getSavedCocktailsOrderedByTimeSaved()
            }
        }
    }

    @Query("SELECT * FROM saved_cocktails_table ORDER BY name ASC")
    fun getSavedCocktailsOrderedByName(): Flow<List<SavedCocktailEntity>>

    @Query("SELECT * FROM saved_cocktails_table ORDER BY timestampMillis DESC")
    fun getSavedCocktailsOrderedByTimeSaved(): Flow<List<SavedCocktailEntity>>

    @Query("SELECT * FROM saved_cocktails_table WHERE name = :name LIMIT 1")
    suspend fun getSavedCocktailByName(name: String): SavedCocktailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedCocktailEntity: SavedCocktailEntity)

    @Delete
    suspend fun delete(savedCocktailEntity: SavedCocktailEntity)

    @Query("DELETE FROM saved_cocktails_table")
    suspend fun deleteAll()

}