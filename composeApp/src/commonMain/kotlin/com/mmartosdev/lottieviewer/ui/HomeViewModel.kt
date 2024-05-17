package com.mmartosdev.lottieviewer.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmartosdev.lottieviewer.data.FileStore
import io.github.alexzhirkevich.compottie.LottieComposition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URI

class HomeViewModel(
    private val fileStore: FileStore
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Default())
    val state: StateFlow<HomeScreenUiState>
        get() = _state

    fun onUriReadyToParse(uri: URI) {
        viewModelScope.launch {
            _state.value = _state.value.copy(LottieParseState.Parsing())
            fileStore.readFileContent(uri).collect {
                _state.value = _state.value.copy(LottieParseState.Parsing(it))
            }
        }
    }

    fun onLottieCompositionReady(composition: LottieComposition) {
        viewModelScope.launch {
            _state.value = HomeScreenUiState.LottiePlayer(composition)
        }
    }

    fun onClearError() {
        viewModelScope.launch {
            _state.value = HomeScreenUiState.Default()
        }
    }

    fun onFailed() {
        viewModelScope.launch {
            _state.value = _state.value.copy(LottieParseState.Failed)
        }
    }
}

sealed interface HomeScreenUiState {
    val lottieParseState: LottieParseState?

    data class Default(
        override val lottieParseState: LottieParseState? = null,
    ) : HomeScreenUiState

    data class LottiePlayer(
        val composition: LottieComposition,
        override val lottieParseState: LottieParseState? = null,
    ) : HomeScreenUiState
}

fun HomeScreenUiState.copy(lottieParseState: LottieParseState?) =
    when (this) {
        is HomeScreenUiState.Default -> copy(lottieParseState = lottieParseState)
        is HomeScreenUiState.LottiePlayer -> copy(lottieParseState = lottieParseState)
    }

@Immutable
sealed interface LottieParseState {
    @Immutable
    data class Parsing(val lottieAnimation: String? = null) : LottieParseState

    @Immutable
    data object Failed : LottieParseState
}
