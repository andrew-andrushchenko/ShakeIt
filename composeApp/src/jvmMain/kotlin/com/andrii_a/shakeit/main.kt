package com.andrii_a.shakeit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ShakeIt",
    ) {
        App()
    }
}