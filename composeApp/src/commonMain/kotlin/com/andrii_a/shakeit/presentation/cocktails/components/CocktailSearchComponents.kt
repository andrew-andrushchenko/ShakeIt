package com.andrii_a.shakeit.presentation.cocktails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.SavedSearch
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andrii_a.shakeit.domain.model.SearchHistoryItem
import com.andrii_a.shakeit.presentation.common.EmptyContentBanner
import org.jetbrains.compose.resources.stringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.clear_search_history
import shakeit.composeapp.generated.resources.search_history_empty

@Composable
fun CocktailSearchFilterOptionsRow(
    options: List<String>,
    selectedOption: String? = null,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {
    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(count = options.size) { index ->
            val option = options[index]

            FilterChip(
                selected = option == selectedOption,
                label = {
                    Text(text = option)
                },
                onClick = {
                    onOptionSelected(option)
                }
            )
        }
    }
}

@Composable
fun SearchHistoryList(
    searchHistory: List<SearchHistoryItem>,
    onItemSelected: (SearchHistoryItem) -> Unit,
    onRemoveItem: (SearchHistoryItem) -> Unit,
    onClearSearchHistory: () -> Unit
) {
    LazyColumn {
        if (searchHistory.isEmpty()) {
            item {
                EmptyContentBanner(
                    imageVector = Icons.Outlined.SavedSearch,
                    message = stringResource(Res.string.search_history_empty),
                    modifier = Modifier.fillParentMaxSize()
                )
            }
        }

        items(
            count = searchHistory.size,
            key = { index -> searchHistory[index].id }
        ) { index ->
            val recentSearchItem = searchHistory[index]

            ListItem(
                headlineContent = { Text(text = recentSearchItem.title) },
                leadingContent = {
                    Icon(
                        Icons.Default.History,
                        contentDescription = null
                    )
                },
                trailingContent = {
                    IconButton(onClick = { onRemoveItem(recentSearchItem) }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                modifier = Modifier
                    .clickable(onClick = { onItemSelected(recentSearchItem) })
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }

        if (searchHistory.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    TextButton(
                        onClick = onClearSearchHistory,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(text = stringResource(Res.string.clear_search_history))
                    }
                }
            }
        }
    }
}