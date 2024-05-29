import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mmartosdev.lottieviewer.data.FileDesc

@Composable
expect fun Modifier.onDragAndDrop(
    onDragStart: () -> Unit = {},
    onDragExit: () -> Unit = {},
    onSingleFileDropped: (FileDesc) -> Unit,
): Modifier