package com.andrii_a.shakeit.presentation.cocktails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.andrii_a.shakeit.domain.model.Ingredient
import org.jetbrains.compose.resources.stringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.ingredient_type_formatted

@Composable
fun IngredientDetailsBottomSheetContent(ingredient: Ingredient) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IngredientItem(
            ingredient = ingredient,
            onClick = {},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(
                Res.string.ingredient_type_formatted,
                ingredient.type
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        HorizontalDivider()

        Text(text = ingredient.description)
    }
}