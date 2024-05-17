package com.mmartosdev.lottieviewer.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
internal fun AppTheme(
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        content = { Surface(content = content) }
    )
}
