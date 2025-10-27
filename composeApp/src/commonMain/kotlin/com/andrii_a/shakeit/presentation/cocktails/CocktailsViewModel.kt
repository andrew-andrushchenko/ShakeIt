package com.andrii_a.shakeit.presentation.cocktails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.SavedCocktail
import com.andrii_a.shakeit.domain.model.SearchHistoryItem
import com.andrii_a.shakeit.domain.repository.CocktailRepository
import com.andrii_a.shakeit.domain.repository.SavedCocktailsRepository
import com.andrii_a.shakeit.domain.repository.SearchHistoryRepository
import com.andrii_a.shakeit.domain.util.Resource
import com.andrii_a.shakeit.presentation.navigation.Screen
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class CocktailsViewModel(
    private val cocktailRepository: CocktailRepository,
    private val savedCocktailsRepository: SavedCocktailsRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<CocktailsUiState> = MutableStateFlow(CocktailsUiState())
    val state = combine(
        searchHistoryRepository.getSearchHistory(),
        _state
    ) { searchHistory, state ->
        state.copy(
            searchHistory = searchHistory
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    init {
        onEvent(CocktailsEvent.RequestCocktails)

        val cocktailId = savedStateHandle.toRoute<Screen.Cocktails>().selectedCocktailId

        if (cocktailId != null) {
            onEvent(CocktailsEvent.SelectCocktailFromFavourites(cocktailId))
        }
    }

    private var cocktailsRequestJob: Job? = null

    fun onEvent(event: CocktailsEvent) {
        when (event) {
            is CocktailsEvent.RequestCocktails -> {
                cocktailsRequestJob?.cancel()

                cocktailsRequestJob = viewModelScope.launch {
                    val searchOptions = state.value.searchOptions

                    cocktailRepository
                        .getCocktails(
                            name = searchOptions.name,
                            hasAlcohol = searchOptions.hasAlcohol,
                            category = searchOptions.category,
                            glass = searchOptions.glass,
                            ingredients = true
                        )
                        .cachedIn(viewModelScope)
                        .collect { pagingData ->
                            _state.update {
                                it.copy(cocktailsPagingData = pagingData)
                            }
                        }
                }
            }

            is CocktailsEvent.SelectCocktail -> {
                viewModelScope.launch {
                    val savedCocktail = savedCocktailsRepository.getSavedCocktailByName(event.cocktail.name)
                    val isCocktailInFavouritesList = savedCocktail != null

                    _state.update {
                        it.copy(
                            selectedCocktail = event.cocktail,
                            isSelectedCocktailInFavouritesList = isCocktailInFavouritesList
                        )
                    }
                }
            }

            is CocktailsEvent.SelectCocktailFromFavourites -> {
                viewModelScope.launch {
                    cocktailRepository
                        .getCocktail(event.cocktailId)
                        .collect { result ->
                            when (result) {
                                is Resource.Empty, Resource.Loading -> Unit
                                is Resource.Error -> {
                                    _state.update {
                                        it.copy(
                                            selectedCocktail = null,
                                            isSelectedCocktailInFavouritesList = false,
                                            error = result.reason
                                        )
                                    }
                                }
                                is Resource.Success<Cocktail> -> {
                                    val cocktail = result.value
                                    val savedCocktail = savedCocktailsRepository.getSavedCocktailByName(cocktail.name)
                                    val isCocktailInFavouritesList = savedCocktail != null

                                    _state.update {
                                        it.copy(
                                            selectedCocktail = cocktail,
                                            isSelectedCocktailInFavouritesList = isCocktailInFavouritesList,
                                            error = null
                                        )
                                    }
                                }
                            }
                        }
                }
            }

            is CocktailsEvent.CleanCocktailSelection -> {
                _state.update {
                    it.copy(
                        selectedCocktail = null
                    )
                }
            }

            is CocktailsEvent.UnsaveCocktail -> {
                unsaveCocktail(event.cocktail)
            }

            is CocktailsEvent.SaveCocktail -> {
                saveCocktail(event.cocktail)
            }

            is CocktailsEvent.ShowIngredientDetailDialog -> {
                _state.update {
                    it.copy(
                        selectedIngredient = event.ingredient
                    )
                }
            }

            is CocktailsEvent.DismissIngredientDetailDialog -> {
                _state.update {
                    it.copy(
                        selectedIngredient = null
                    )
                }
            }

            is CocktailsEvent.UpdateSearchOptions -> {
                addSearchHistoryItem(event.options.name)

                _state.update {
                    it.copy(
                        searchOptions = event.options
                    )
                }
            }

            is CocktailsEvent.RemoveSearchHistoryItem -> {
                removeSearchHistoryItem(event.item)
            }

            is CocktailsEvent.ClearSearchHistory -> {
                clearSearchHistory()
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun saveCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            val cocktailToSave = SavedCocktail(
                name = cocktail.name,
                imageUrl = cocktail.imageUrl,
                cocktailId = cocktail.id,
                timestampMillis = Clock.System.now().toEpochMilliseconds()
            )

            withContext(NonCancellable) {
                savedCocktailsRepository.save(cocktailToSave)
            }

            _state.update {
                it.copy(isSelectedCocktailInFavouritesList = true)
            }
        }
    }

    private fun unsaveCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            withContext(NonCancellable) {
                val savedCocktail = savedCocktailsRepository.getSavedCocktailByName(cocktail.name)
                    ?: return@withContext
                savedCocktailsRepository.unsave(savedCocktail)
            }
        }

        _state.update {
            it.copy(isSelectedCocktailInFavouritesList = false)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun addSearchHistoryItem(query: String?) {
        if (query == null || query.isEmpty()) return

        viewModelScope.launch {
            withContext(NonCancellable) {
                val itemToModify = searchHistoryRepository.getSearchHistoryItemByTitle(query)
                itemToModify?.let {
                    searchHistoryRepository.updateItem(
                        it.copy(timestampMillis = Clock.System.now().toEpochMilliseconds())
                    )
                } ?: run {
                    val newSearchHistoryItem = SearchHistoryItem(
                        title = query,
                        timestampMillis = Clock.System.now().toEpochMilliseconds()
                    )

                    searchHistoryRepository.addItem(newSearchHistoryItem)
                }
            }
        }
    }

    private fun removeSearchHistoryItem(item: SearchHistoryItem) {
        viewModelScope.launch {
            withContext(NonCancellable) {
                searchHistoryRepository.removeItem(item)
            }
        }
    }

    private fun clearSearchHistory() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                searchHistoryRepository.clearSearchHistory()
            }
        }
    }

}