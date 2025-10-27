package com.andrii_a.shakeit.presentation.favourites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.andrii_a.shakeit.domain.model.SortOrder
import com.andrii_a.shakeit.presentation.common.OptionsMenu
import com.andrii_a.shakeit.presentation.util.titleRes
import org.jetbrains.compose.resources.stringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.favourites

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    state: FavouritesUiState,
    onEvent: (FavouritesEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val colors = TopAppBarDefaults.topAppBarColors()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(Res.string.favourites)) },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(FavouritesEvent.ToggleListOrderMenu(isExpanded = !state.isOrderMenuExpanded))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.Sort,
                            contentDescription = null
                        )
                    }
                },
                colors = colors,
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        val colorTransitionFraction by remember(scrollBehavior) {
            derivedStateOf {
                val overlappingFraction = scrollBehavior.state.overlappedFraction
                if (overlappingFraction > 0.01f) 1f else 0f
            }
        }

        val targetColor by animateColorAsState(
            targetValue = lerp(
                colors.containerColor,
                colors.scrolledContainerColor,
                FastOutLinearInEasing.transform(colorTransitionFraction)
            ),
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .animateContentSize()
        ) {
            AnimatedVisibility(
                visible = state.isOrderMenuExpanded,
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .background(targetColor)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (state.isOrderMenuExpanded) {
                    OptionsMenu(
                        optionsStringRes = SortOrder.entries.map { it.titleRes },
                        selectedOption = state.sortOrder.ordinal,
                        onOptionSelected = { option ->
                            onEvent(FavouritesEvent.ChangeSortOrder(SortOrder.entries[option]))
                            onEvent(FavouritesEvent.ToggleListOrderMenu(isExpanded = false))
                        }
                    )
                }
            }

            SavedCocktailsGrid(
                savedCocktails = state.cocktails,
                onClick = { onEvent(FavouritesEvent.SelectCocktail(it)) },
                onUnsaveClick = { onEvent(FavouritesEvent.UnsaveCocktail(it)) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}