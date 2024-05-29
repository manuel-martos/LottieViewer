import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalWindowInfo
import com.mmartosdev.lottieviewer.data.FileDesc

data class JsFileDesc(val file: JsAny) : FileDesc

external fun registerDragAndDropListener(
    onDragOver: () -> Unit,
    onDragLeave: () -> Unit,
    onDrop: (JsAny) -> Unit,
)

external fun unregisterDragAndDropListener()

@Composable
actual fun Modifier.onDragAndDrop(
    onDragStart: () -> Unit,
    onDragExit: () -> Unit,
    onSingleFileDropped: (FileDesc) -> Unit,
): Modifier = composed {
    // Hacky way to bind JS and Compose, specially returning new instance of raw Modifier.
    DisposableEffect(LocalWindowInfo.current) {
        registerDragAndDropListener(
            onDragOver = { onDragStart() },
            onDragLeave = { onDragExit() },
            onDrop = { onSingleFileDropped(JsFileDesc(it)) },
        )
        onDispose {
            unregisterDragAndDropListener()
        }
    }
    Modifier
}
