package com.andrii_a.shakeit.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrii_a.shakeit.domain.model.SavedCocktail
import com.andrii_a.shakeit.domain.model.SortOrder
import com.andrii_a.shakeit.domain.repository.SavedCocktailsRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesViewModel(
    private val savedCocktailsRepository: SavedCocktailsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<FavouritesUiState> = MutableStateFlow(FavouritesUiState())
    val state: StateFlow<FavouritesUiState> = _state.asStateFlow()

    init {
        onEvent(FavouritesEvent.ChangeSortOrder(sortOrder = SortOrder.BY_DATE_SAVED))
    }

    private val navigationEventChannel: Channel<FavouritesNavigationEvent> = Channel()
    val navigationEventFlow = navigationEventChannel.receiveAsFlow()

    fun onEvent(event: FavouritesEvent) {
        when (event) {
            is FavouritesEvent.ChangeSortOrder -> {
                changeSortOrder(event.sortOrder)
            }

            is FavouritesEvent.SelectCocktail -> {
                selectCocktail(event.cocktail)
            }

            is FavouritesEvent.UnsaveCocktail -> {
                unsaveCocktail(event.cocktail)
            }

            is FavouritesEvent.UnsaveAllCocktails -> {
                unsaveAllCocktails()
            }

            is FavouritesEvent.ToggleListOrderMenu -> {
                _state.update {
                    it.copy(isOrderMenuExpanded = event.isExpanded)
                }
            }
        }
    }

    private fun unsaveAllCocktails() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                savedCocktailsRepository.unsaveAll()
            }
        }
    }

    private fun unsaveCocktail(cocktail: SavedCocktail) {
        viewModelScope.launch {
            withContext(NonCancellable) {
                savedCocktailsRepository.unsave(cocktail)
            }
        }
    }

    private fun changeSortOrder(sortOrder: SortOrder) {
        _state.update {
            it.copy(sortOrder = sortOrder)
        }

        viewModelScope.launch {
            savedCocktailsRepository.getSavedCocktails(sortOrder)
                .collect { cocktails ->
                    _state.update { it.copy(cocktails = cocktails) }
                }

        }
    }

    private fun selectCocktail(cocktail: SavedCocktail) {
        viewModelScope.launch {
            navigationEventChannel.send(
                FavouritesNavigationEvent.NavigateToCocktailDetail(cocktail.cocktailId)
            )
        }
    }

}