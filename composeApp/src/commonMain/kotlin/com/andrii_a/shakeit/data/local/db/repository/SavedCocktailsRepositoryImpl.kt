package com.andrii_a.shakeit.data.local.db.repository

import com.andrii_a.shakeit.data.local.db.dao.SavedCocktailsDao
import com.andrii_a.shakeit.data.util.asEntity
import com.andrii_a.shakeit.data.util.toSavedCocktail
import com.andrii_a.shakeit.domain.model.SavedCocktail
import com.andrii_a.shakeit.domain.model.SortOrder
import com.andrii_a.shakeit.domain.repository.SavedCocktailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalCoroutinesApi::class)
class SavedCocktailsRepositoryImpl(private val dao: SavedCocktailsDao) : SavedCocktailsRepository {

    override fun getSavedCocktails(sortOrder: SortOrder): Flow<List<SavedCocktail>> {
        return dao.getSavedCocktails(sortOrder).flatMapLatest { entityList ->
            flow {
                val savedCocktails = entityList.map { it.toSavedCocktail() }
                emit(savedCocktails)
            }
        }
    }

    override suspend fun getSavedCocktailByName(name: String): SavedCocktail? {
        return dao.getSavedCocktailByName(name)?.toSavedCocktail()
    }

    override suspend fun save(cocktail: SavedCocktail) {
        dao.insert(cocktail.asEntity())
    }

    override suspend fun unsave(cocktail: SavedCocktail) {
        dao.delete(cocktail.asEntity())
    }

    override suspend fun unsaveAll() {
        dao.deleteAll()
    }
}