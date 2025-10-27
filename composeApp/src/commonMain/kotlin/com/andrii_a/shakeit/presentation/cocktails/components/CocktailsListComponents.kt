package com.andrii_a.shakeit.presentation.cocktails.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.CocktailCategory
import com.andrii_a.shakeit.domain.model.CocktailGlass
import com.andrii_a.shakeit.domain.model.SearchHistoryItem
import com.andrii_a.shakeit.presentation.common.EmptyContentBanner
import com.andrii_a.shakeit.presentation.common.ErrorBanner
import com.andrii_a.shakeit.presentation.common.ErrorItem
import com.andrii_a.shakeit.presentation.common.LoadingListItem
import com.andrii_a.shakeit.presentation.common.VerticalScrollBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.cocktail_category
import shakeit.composeapp.generated.resources.cocktail_glass
import shakeit.composeapp.generated.resources.navigate_back
import shakeit.composeapp.generated.resources.type_something

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun CocktailsListPane(
    lazyCocktailItems: LazyPagingItems<Cocktail>,
    searchOptions: CocktailSearchOptions,
    searchHistory: List<SearchHistoryItem>,
    onCocktailClick: (Cocktail) -> Unit,
    onSearchCocktail: () -> Unit,
    onUpdateSearchOptions: (CocktailSearchOptions) -> Unit,
    onRemoveSearchHistoryItem: (SearchHistoryItem) -> Unit,
    onClearSearchHistory: () -> Unit,
    selectedCocktail: Cocktail? = null,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val searchBarState = rememberSearchBarState()
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val textFieldState = rememberTextFieldState(
        initialText = searchOptions.name.orEmpty()
    )

    val leadingIcon =
        @Composable {
            IconButton(
                onClick = {
                    scope.launch {
                        searchBarState.animateToCollapsed()
                    }
                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(Res.string.navigate_back)
                )
            }
        }

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = { query ->
                    if (query != searchOptions.name) {
                        val searchOptions = searchOptions.copy(name = query)
                        onUpdateSearchOptions(searchOptions)
                    }

                    onSearchCocktail()
                    scope.launch { searchBarState.animateToCollapsed() }
                },
                placeholder = { Text(stringResource(Res.string.type_something)) },
                leadingIcon = if (searchBarState.currentValue == SearchBarValue.Expanded) leadingIcon else null,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onSearchCocktail()
                            scope.launch {
                                searchBarState.animateToCollapsed()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = stringResource(Res.string.navigate_back)
                        )
                    }
                },
                modifier = Modifier,
            )
        }

    Scaffold(
        topBar = {
            TopSearchBar(
                scrollBehavior = scrollBehavior,
                state = searchBarState,
                inputField = inputField
            )
            ExpandedFullScreenSearchBar(
                state = searchBarState,
                inputField = inputField,
            ) {
                Text(
                    text = stringResource(Res.string.cocktail_category),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )

                CocktailSearchFilterOptionsRow(
                    options = CocktailCategory.entries.map { it.categoryName }.toList(),
                    selectedOption = searchOptions.category?.categoryName,
                    onOptionSelected = {
                        val selectedCategory =
                            if (searchOptions.category != CocktailCategory from it) CocktailCategory from it else null

                        onUpdateSearchOptions(
                            searchOptions.copy(category = selectedCategory)
                        )
                    }
                )

                Text(
                    text = stringResource(Res.string.cocktail_glass),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )

                CocktailSearchFilterOptionsRow(
                    options = CocktailGlass.entries.map { it.glassName }.toList(),
                    selectedOption = searchOptions.glass?.glassName,
                    onOptionSelected = {
                        val selectedGlass =
                            if (searchOptions.glass != CocktailGlass from it) CocktailGlass from it else null

                        onUpdateSearchOptions(
                            searchOptions.copy(glass = selectedGlass)
                        )
                    }
                )

                SearchHistoryList(
                    searchHistory = searchHistory,
                    onItemSelected = { item ->
                        onUpdateSearchOptions(searchOptions.copy(name = item.title))
                        onSearchCocktail()
                        scope.launch {
                            searchBarState.animateToCollapsed()
                        }
                    },
                    onRemoveItem = { item ->
                        onRemoveSearchHistoryItem(item)
                    },
                    onClearSearchHistory = onClearSearchHistory
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val listState = rememberLazyListState()

            LazyColumn(
                state = listState,
                contentPadding = innerPadding,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                when (lazyCocktailItems.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        if (lazyCocktailItems.itemCount > 0) {
                            items(
                                count = lazyCocktailItems.itemCount,
                                key = lazyCocktailItems.itemKey { it.id }
                            ) { index ->
                                val cocktail = lazyCocktailItems[index]
                                cocktail?.let {
                                    val isSelected = selectedCocktail?.id == it.id
                                    val radius = if (isSelected) {
                                        16.dp
                                    } else {
                                        64.dp / 2f
                                    }
                                    val cornerRadius by animateDpAsState(targetValue = radius)

                                    CocktailItem(
                                        cocktail = it,
                                        onClick = onCocktailClick,
                                        isSelected = isSelected,
                                        //imageShape = if (index % 2 == 0) CloverShape else BottleCapShape,
                                        imageShape = RoundedCornerShape(cornerRadius),
                                        imageSize = 64.dp
                                    )
                                }
                            }
                        } else {
                            item {
                                EmptyContentBanner(modifier = Modifier.fillParentMaxSize())
                            }
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            LoadingListItem(modifier = Modifier.fillParentMaxSize())
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            ErrorBanner(
                                onRetry = lazyCocktailItems::retry,
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }
                }

                when (lazyCocktailItems.loadState.append) {
                    is LoadState.NotLoading -> Unit

                    is LoadState.Loading -> {
                        item {
                            LoadingListItem(modifier = Modifier.fillParentMaxWidth())
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            ErrorItem(
                                onRetry = lazyCocktailItems::retry,
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            )
                        }
                    }
                }
            }

            VerticalScrollBar(
                state = listState,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .align(Alignment.CenterEnd)
            )

        }
    }
}

@Composable
fun CocktailItem(
    cocktail: Cocktail,
    onClick: (Cocktail) -> Unit,
    isSelected: Boolean,
    imageShape: Shape = CircleShape,
    imageSize: Dp = 64.dp,
    itemShape: Shape = RoundedCornerShape(16.dp),
    modifier: Modifier = Modifier
) {
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 1.dp else 0.dp
    )

    val animatedPadding by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp
    )

    ListItem(
        headlineContent = {
            Text(
                text = cocktail.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(cocktail.imageUrl)
                    .crossfade(durationMillis = 1000)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = "Cocktail image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(imageSize)
                    .clip(imageShape)
            )
        },
        supportingContent = {
            Text(
                text = cocktail.category.categoryName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingContent = {
            AnimatedVisibility(
                visible = !isSelected,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                FilledTonalIconButton(onClick = { onClick(cocktail) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        },
        tonalElevation = elevation,
        modifier = modifier
            .padding(animatedPadding)
            .clip(itemShape)
            .then(
                if (isSelected) {
                    Modifier
                } else {
                    Modifier.clickable { onClick(cocktail) }
                }
            )
    )
}