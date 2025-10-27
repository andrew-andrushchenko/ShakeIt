package com.andrii_a.shakeit.presentation.cocktails

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldPaneScope
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.presentation.cocktails.components.CocktailDetailPane
import com.andrii_a.shakeit.presentation.cocktails.components.CocktailsListPane
import com.andrii_a.shakeit.presentation.cocktails.components.IngredientDetailsBottomSheetContent
import com.andrii_a.shakeit.presentation.common.EmptyContentBanner
import com.andrii_a.shakeit.presentation.common.PlatformBackHandler
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.select_cocktail_to_see_description

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CocktailsScreen(
    state: CocktailsUiState,
    onEvent: (CocktailsEvent) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Cocktail>()
    val scope = rememberCoroutineScope()

    PlatformBackHandler(
        enabled = navigator.canNavigateBack(),
        onNavigateBack = {
            scope.launch {
                navigator.navigateBack()
            }.invokeOnCompletion {
                onEvent(CocktailsEvent.CleanCocktailSelection)
            }
        }
    )

    LaunchedEffect(state) {
        if (state.selectedCocktail != null) {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, state.selectedCocktail)
        }
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            CocktailsListPane(
                state = state,
                onEvent = onEvent,
                onOpenCocktailsDetail = { cocktail ->
                    onEvent(CocktailsEvent.SelectCocktail(cocktail))
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, cocktail)
                    }
                }
            )
        },
        detailPane = {
            CocktailsDetailPane(
                state = state,
                onEvent = onEvent,
                navigator = navigator
            )
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun ThreePaneScaffoldPaneScope.CocktailsListPane(
    state: CocktailsUiState,
    onEvent: (CocktailsEvent) -> Unit,
    onOpenCocktailsDetail: (Cocktail) -> Unit
) {
    //val scope = rememberCoroutineScope()

    AnimatedPane(
        modifier = Modifier.preferredWidth(350.dp)
    ) {
        val lazyCocktailItems = state.cocktails.collectAsLazyPagingItems()

        CocktailsListPane(
            lazyCocktailItems = lazyCocktailItems,
            searchOptions = state.searchOptions,
            searchHistory = state.searchHistory,
            selectedCocktail = state.selectedCocktail,
            onSearchCocktail = {
                onEvent(CocktailsEvent.RequestCocktails)
            },
            onUpdateSearchOptions = { options ->
                onEvent(CocktailsEvent.UpdateSearchOptions(options))
            },
            onRemoveSearchHistoryItem = { item ->
                onEvent(CocktailsEvent.RemoveSearchHistoryItem(item))
            },
            onClearSearchHistory = {
                onEvent(CocktailsEvent.ClearSearchHistory)
            },
            onCocktailClick = onOpenCocktailsDetail/*{ cocktail ->
                onEvent(CocktailsEvent.SelectCocktail(cocktail))
                scope.launch {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, cocktail)
                }
            }*/
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ThreePaneScaffoldPaneScope.CocktailsDetailPane(
    state: CocktailsUiState,
    onEvent: (CocktailsEvent) -> Unit,
    navigator: ThreePaneScaffoldNavigator<Cocktail>
) {
    val scope = rememberCoroutineScope()

    AnimatedPane {
        AnimatedContent(targetState = navigator.currentDestination?.contentKey) { cocktail ->
            when {
                cocktail != null -> {
                    val bottomSheetState = rememberModalBottomSheetState()

                    CocktailDetailPane(
                        cocktail = cocktail,
                        isCocktailInFavouritesList = state.isSelectedCocktailInFavouritesList,
                        onSaveCocktail = { cocktail ->
                            onEvent(CocktailsEvent.SaveCocktail(cocktail))
                        },
                        onUnsaveCocktail = { cocktail ->
                            onEvent(CocktailsEvent.UnsaveCocktail(cocktail))
                        },
                        onShowIngredientDetailDialog = { ingredient ->
                            onEvent(CocktailsEvent.ShowIngredientDetailDialog(ingredient))
                        },
                        onClosePane = {
                            onEvent(CocktailsEvent.CleanCocktailSelection)
                            scope.launch {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                            }
                        },
                    )

                    if (state.selectedIngredient != null) {
                        ModalBottomSheet(
                            onDismissRequest = { onEvent(CocktailsEvent.DismissIngredientDetailDialog) },
                            sheetState = bottomSheetState
                        ) {
                            IngredientDetailsBottomSheetContent(ingredient = state.selectedIngredient)
                        }
                    }
                }

                else -> {
                    EmptyContentBanner(
                        imageVector = Icons.Outlined.LocalDrink,
                        message = stringResource(Res.string.select_cocktail_to_see_description)
                    )
                }
            }
        }
    }
}