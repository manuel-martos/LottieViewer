package com.mmartosdev.lottieviewer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmartosdev.lottieviewer.data.FileDesc
import com.mmartosdev.lottieviewer.data.createFileStore
import com.mmartosdev.lottieviewer.utils.dashedBorder
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieComposition
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.awaitOrNull
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import onDragAndDrop

@Composable
fun MainScreen(
    viewModel: HomeViewModel = viewModel { HomeViewModel(createFileStore()) },
    modifier: Modifier = Modifier,
) {
    val homeScreenUiState by viewModel.state.collectAsStateWithLifecycle()
    when (val uiState = homeScreenUiState) {
        is HomeScreenUiState.Default ->
            HomeScreenDefault(
                lottieParseState = uiState.lottieParseState,
                onUriReadyToParse = { viewModel.onUriReadyToParse(it) },
                onLottieParsingSucceeded = { viewModel.onLottieCompositionReady(it) },
                onLottieParsingFailed = { viewModel.onFailed() },
                onClearError = { viewModel.onClearError() },
                modifier = modifier,
            )

        is HomeScreenUiState.LottiePlayer ->
            HomeScreenLottiePlayer(
                composition = uiState.composition,
                lottieParseState = uiState.lottieParseState,
                onUriReadyToParse = { viewModel.onUriReadyToParse(it) },
                onLottieParsingSucceeded = { viewModel.onLottieCompositionReady(it) },
                onLottieParsingFailed = { viewModel.onFailed() },
                modifier = modifier,
            )
    }
}

@Composable
fun HomeScreenDefault(
    lottieParseState: LottieParseState?,
    onUriReadyToParse: (FileDesc) -> Unit,
    onLottieParsingSucceeded: (LottieComposition) -> Unit,
    onLottieParsingFailed: () -> Unit,
    onClearError: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        val showError = lottieParseState is LottieParseState.Failed
        var isDragging by rememberSaveable { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "LottieViewer",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .dashedBorder(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = if (isDragging) 1f else 0.2f),
                        shape = RoundedCornerShape(16.dp),
                        strokeWidth = 4.dp,
                        dashWidth = 12.dp,
                        gapWidth = 8.dp,
                    )
                    .onDragAndDrop(
                        onDragStart = {
                            isDragging = true
                            if (showError) {
                                onClearError()
                            }
                        },
                        onDragExit = {
                            isDragging = false
                        },
                        onSingleFileDropped = {
                            onUriReadyToParse(it)
                            isDragging = false
                        }
                    )
                    .padding(32.dp),
            )
            {
                Text(
                    text = "Drag & drop any Lottie file to visualise",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
                AnimatedVisibility(
                    visible = showError,
                    modifier = modifier.padding(top = 64.dp),
                ) {
                    Text(
                        text = "Invalid file! Make sure you're dropping a valid Lottie file",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
        (lottieParseState as? LottieParseState.Parsing)?.run {
            LottieParsingLoader(
                lottieParseState = this,
                onLottieParsingSucceeded = onLottieParsingSucceeded,
                onLottieParsingFailed = onLottieParsingFailed,
            )
        }
    }
}

@Composable
fun LottieParsingLoader(
    lottieParseState: LottieParseState.Parsing,
    onLottieParsingSucceeded: (LottieComposition) -> Unit,
    onLottieParsingFailed: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f))
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
    }
    lottieParseState.lottieAnimation?.run {
        parseLottieAnimation(
            lottieAnimation = this,
            onLottieParsingSucceeded = onLottieParsingSucceeded,
            onLottieParsingFailed = onLottieParsingFailed
        )
    }
}

@Composable
fun parseLottieAnimation(
    lottieAnimation: String,
    onLottieParsingSucceeded: (LottieComposition) -> Unit,
    onLottieParsingFailed: () -> Unit,
) {
    val compositionResult =
        rememberLottieComposition(LottieCompositionSpec.JsonString(lottieAnimation))
    var composition by remember { mutableStateOf<LottieComposition?>(null) }
    LaunchedEffect(composition) {
        composition = compositionResult.awaitOrNull()
        composition?.run {
            onLottieParsingSucceeded(this)
        } ?: onLottieParsingFailed()
    }
}

@Composable
fun HomeScreenLottiePlayer(
    composition: LottieComposition,
    lottieParseState: LottieParseState?,
    onUriReadyToParse: (FileDesc) -> Unit,
    onLottieParsingSucceeded: (LottieComposition) -> Unit,
    onLottieParsingFailed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPlaying by remember { mutableStateOf(true) }
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(16.dp),
        ) {
            val progress = animateLottieCompositionAsState(
                isPlaying = isPlaying,
                composition = composition,
                restartOnPlay = false,
                iterations = LottieConstants.IterateForever,
            )
            LottieAnimation(
                composition = composition,
                progress = { progress.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(2.dp, MaterialTheme.colorScheme.outline)
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .onDragAndDrop(
                        onSingleFileDropped = {
                            onUriReadyToParse(it)
                        },
                    ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { isPlaying = !isPlaying },
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Play" else "Pause",
                    )
                }
                Slider(
                    value = progress.progress,
                    onValueChange = { /* no-op */ },
                    modifier = Modifier.fillMaxWidth(0.7f),
                )
            }
        }

        (lottieParseState as? LottieParseState.Parsing)?.run {
            LottieParsingLoader(
                lottieParseState = this,
                onLottieParsingSucceeded = onLottieParsingSucceeded,
                onLottieParsingFailed = onLottieParsingFailed,
            )
        }
    }
}