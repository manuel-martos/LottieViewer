package com.mmartosdev.lottieviewer.utils

import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragData
import androidx.compose.ui.draganddrop.dragData
import com.mmartosdev.lottieviewer.data.FileDesc
import java.net.URI

data class UriFileDesc(val uri: URI) : FileDesc

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun Modifier.onDragAndDrop(
    onDragStart: () -> Unit,
    onDragExit: () -> Unit,
    onSingleFileDropped: (FileDesc) -> Unit,
): Modifier = composed {
    val target = remember {
        object : DragAndDropTarget {
            override fun onEntered(event: DragAndDropEvent) {
                onDragStart()
            }

            override fun onExited(event: DragAndDropEvent) {
                onDragExit()
            }

            override fun onDrop(event: DragAndDropEvent): Boolean =
                extractSingleFile(event.dragData())?.run {
                    onSingleFileDropped(this)
                    true
                } ?: false
        }
    }
    dragAndDropTarget(
        shouldStartDragAndDrop = { true },
        target = target,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
private fun extractSingleFile(dragData: DragData): FileDesc? =
    (dragData as? DragData.FilesList)?.run {
        val files = readFiles()
        if (files.size == 1) {
            return UriFileDesc(URI.create(files[0]))
        }
        null
    }