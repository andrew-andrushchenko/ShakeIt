package com.andrii_a.shakeit.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
actual fun PlatformBackHandler(enabled: Boolean, onNavigateBack: () -> Unit) {
}

@Composable
actual fun VerticalScrollBar(
    state: LazyListState,
    modifier: Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    val isHovered by interactionSource.collectIsHoveredAsState()

    val targetAlpha = if (state.isScrollInProgress || isHovered) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 2000

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    VerticalScrollbar(
        adapter = rememberScrollbarAdapter(
            scrollState = state
        ),
        interactionSource = interactionSource,
        modifier = modifier.alpha(alpha)
    )
}

@Composable
actual fun HorizontalScrollBar(
    state: LazyListState,
    modifier: Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    val isHovered by interactionSource.collectIsHoveredAsState()

    val targetAlpha = if (state.isScrollInProgress || isHovered) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 2000

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    HorizontalScrollbar(
        adapter = rememberScrollbarAdapter(
            scrollState = state
        ),
        interactionSource = interactionSource,
        modifier = modifier.alpha(alpha)
    )
}