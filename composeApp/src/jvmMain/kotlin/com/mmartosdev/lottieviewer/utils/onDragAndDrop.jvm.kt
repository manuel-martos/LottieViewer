package com.mmartosdev.lottieviewer.utils

import androidx.compose.foundation.ExperimentalFoundationApi
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun Modifier.onDragAndDrop(
  onDragStart: () -> Unit,
  onDragExit: () -> Unit,
  onSingleFileDropped: (FileDesc) -> Unit,
): Modifier = this.composed {
  val dragAndDropTarget: DragAndDropTarget = remember {
    object : DragAndDropTarget {
      override fun onDrop(event: DragAndDropEvent): Boolean =
        (extractSingleFile(event.dragData())?.run {
          onSingleFileDropped(this)
          true
        } ?: false).also {
          println("onDrop")
        }

      override fun onChanged(event: DragAndDropEvent) {
        println("onChanged")
      }

      override fun onEntered(event: DragAndDropEvent) {
        println("onEntered")
      }

      override fun onExited(event: DragAndDropEvent) {
        println("onExited")
      }
    }
  }
  dragAndDropTarget(shouldStartDragAndDrop = { true }, target = dragAndDropTarget)
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