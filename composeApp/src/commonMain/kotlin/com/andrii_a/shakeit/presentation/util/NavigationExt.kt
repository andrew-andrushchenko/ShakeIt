package com.andrii_a.shakeit.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

val NavController.currentScreenClassName: String?
    @Composable
    get() {
        val navBackStackEntry by this.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
            ?.substringBefore("?")
            ?.substringBefore("/")
            ?.substringAfterLast(".")
    }