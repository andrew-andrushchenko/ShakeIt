package com.andrii_a.shakeit.presentation.cocktails.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.andrii_a.shakeit.domain.model.Cocktail
import com.andrii_a.shakeit.domain.model.Ingredient
import com.andrii_a.shakeit.presentation.common.HorizontalScrollBar
import org.jetbrains.compose.resources.stringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.cocktail_image
import shakeit.composeapp.generated.resources.cocktail_ingredient_image
import shakeit.composeapp.generated.resources.cooking_instructions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailPane(
    cocktail: Cocktail,
    onShowIngredientDetailDialog: (Ingredient) -> Unit,
    isCocktailInFavouritesList: Boolean,
    onSaveCocktail: (Cocktail) -> Unit,
    onUnsaveCocktail: (Cocktail) -> Unit,
    onClosePane: () -> Unit,
    tonalElevation: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = tonalElevation,
        modifier = modifier
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = cocktail.name,
                            style = MaterialTheme.typography.titleLarge,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Color.Black.copy(alpha = 0.4f))
                                .padding(8.dp)
                        )
                    },
                    navigationIcon = {
                        FilledTonalIconButton(
                            onClick = onClosePane,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Black.copy(alpha = 0.4f)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        FilledTonalIconButton(
                            onClick = {
                                if (isCocktailInFavouritesList) {
                                    onUnsaveCocktail(cocktail)
                                } else {
                                    onSaveCocktail(cocktail)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isCocktailInFavouritesList) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            },
            contentWindowInsets = WindowInsets.safeDrawing,
        ) { innerPadding ->
            CocktailDetailContent(
                cocktail = cocktail,
                onIngredientClick = { ingredient ->
                    onShowIngredientDetailDialog(ingredient)
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailContent(
    cocktail: Cocktail,
    onIngredientClick: (Ingredient) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        val background = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(cocktail.imageUrl)
                .crossfade(durationMillis = 1000)
                .scale(Scale.FILL)
                .build(),
            contentDescription = stringResource(Res.string.cocktail_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .alpha(0.7f)
                .drawWithContent {
                    drawContent()
                    drawRect(brush = background)
                }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(cocktail.imageUrl)
                    .crossfade(durationMillis = 1000)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = stringResource(Res.string.cocktail_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .align(Alignment.CenterHorizontally)
            )

            /*Text(
                text = stringResource(Res.string.cocktail_ingredients),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )*/

            Box(modifier = Modifier.fillMaxWidth()) {
                val ingredientsListState = rememberLazyListState()

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    state = ingredientsListState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(count = cocktail.ingredients.size) { index ->
                        val ingredient = cocktail.ingredients[index]
                        IngredientItem(
                            ingredient = ingredient,
                            onClick = onIngredientClick,
                            modifier = Modifier.padding(
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            )
                        )
                    }
                }

                HorizontalScrollBar(
                    state = ingredientsListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.BottomCenter)
                )
            }


            Text(
                text = stringResource(Res.string.cooking_instructions),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier
                    //.padding(horizontal = 8.dp)
                    .widthIn(max = 800.dp)
                //.align(Alignment.CenterHorizontally)
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }

                Text(
                    text = cocktail.instructions,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (expanded) Int.MAX_VALUE else 12,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { expanded = !expanded }
                        .animateContentSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClick: (Ingredient) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onClick(ingredient) })
    ) {
        val background = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
                MaterialTheme.colorScheme.tertiaryContainer
            )
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(94.dp)
                    .clip(MaterialShapes.Pill.toShape())
                    .background(brush = background)
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(ingredient.imageUrl)
                    .crossfade(durationMillis = 1000)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = stringResource(Res.string.cocktail_ingredient_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(94.dp)
            )

            ingredient.percentage?.let {
                Badge(modifier = Modifier.align(Alignment.TopEnd)) {
                    Text(text = "$it%")
                }
            }
        }

        Text(
            text = ingredient.name,
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.requiredWidth(96.dp)
        )
    }
}