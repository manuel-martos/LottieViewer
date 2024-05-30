package com.mmartosdev.lottieviewer.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.DragData
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.onExternalDrag
import com.mmartosdev.lottieviewer.data.FileDesc
import java.net.URI

data class UriFileDesc(val uri: URI) : FileDesc

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun Modifier.onDragAndDrop(
    onDragStart: () -> Unit,
    onDragExit: () -> Unit,
    onSingleFileDropped: (FileDesc) -> Unit,
): Modifier = this then onExternalDrag(
    enabled = true,
    onDrag = { },
    onDrop = { extractSingleFile(it.dragData)?.run { onSingleFileDropped(this) } },
    onDragExit = { onDragExit() },
    onDragStart = { onDragStart() },
)

@OptIn(ExperimentalComposeUiApi::class)
private fun extractSingleFile(dragData: DragData): FileDesc? =
    (dragData as? DragData.FilesList)?.run {
        val files = readFiles()
        if (files.size == 1) {
            return UriFileDesc(URI.create(files[0]))
        }
        null
    }