package com.mmartosdev.lottieviewer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mmartosdev.lottieviewer.theme.AppTheme
import com.mmartosdev.lottieviewer.ui.MainScreen

@Composable
internal fun App() = AppTheme {
    MainScreen(modifier = Modifier.fillMaxSize())
}
