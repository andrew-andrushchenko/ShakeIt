package com.andrii_a.shakeit.presentation.common

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformBackHandler(enabled: Boolean, onNavigateBack: () -> Unit) {
}

@Composable
actual fun VerticalScrollBar(
    state: LazyListState,
    modifier: Modifier
) {
}

@Composable
actual fun HorizontalScrollBar(
    state: LazyListState,
    modifier: Modifier
) {
}