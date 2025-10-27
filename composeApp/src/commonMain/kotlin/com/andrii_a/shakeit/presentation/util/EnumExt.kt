package com.andrii_a.shakeit.presentation.util

import com.andrii_a.shakeit.domain.model.SortOrder
import com.andrii_a.shakeit.domain.preferences.AppLanguage
import com.andrii_a.shakeit.domain.preferences.AppTheme
import org.jetbrains.compose.resources.StringResource
import shakeit.composeapp.generated.resources.Res
import shakeit.composeapp.generated.resources.language_english
import shakeit.composeapp.generated.resources.language_polish
import shakeit.composeapp.generated.resources.sort_by_date
import shakeit.composeapp.generated.resources.sort_by_name
import shakeit.composeapp.generated.resources.theme_dark
import shakeit.composeapp.generated.resources.theme_light
import shakeit.composeapp.generated.resources.theme_system

val SortOrder.titleRes: StringResource
    get() = when (this) {
        SortOrder.BY_DATE_SAVED -> Res.string.sort_by_date
        SortOrder.BY_NAME -> Res.string.sort_by_name
    }

val AppTheme.titleRes: StringResource
    get() = when (this) {
        AppTheme.SYSTEM_DEFAULT -> Res.string.theme_system
        AppTheme.LIGHT -> Res.string.theme_light
        AppTheme.DARK -> Res.string.theme_dark
    }

val AppLanguage.titleRes: StringResource
    get() = when (this) {
        AppLanguage.ENGLISH -> Res.string.language_english
        AppLanguage.POLISH -> Res.string.language_polish
    }