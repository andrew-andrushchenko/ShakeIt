package com.andrii_a.shakeit.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.cocktails
import shakeit.composeapp.generated.resources.favourites
import shakeit.composeapp.generated.resources.settings

enum class NavigationDestination(
    val screen: Screen,
    val titleRes: StringResource,
    val iconUnselected: ImageVector,
    val iconSelected: ImageVector
) {
    Cocktails(
        screen = Screen.Cocktails(),
        titleRes = Res.string.cocktails,
        iconUnselected = Icons.Outlined.LocalDrink,
        iconSelected = Icons.Filled.LocalDrink
    ),
    Favourites(
        screen = Screen.Favourites,
        titleRes = Res.string.favourites,
        iconUnselected = Icons.Outlined.FavoriteBorder,
        iconSelected = Icons.Filled.Favorite
    ),
    Settings(
        screen = Screen.Settings,
        titleRes = Res.string.settings,
        iconUnselected = Icons.Outlined.Settings,
        iconSelected = Icons.Filled.Settings
    )
}