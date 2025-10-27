package com.andrii_a.shakeit.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_cocktails_table")
data class SavedCocktailEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageUrl: String,
    val cocktailId: Int,
    val timestampMillis: Long
)
