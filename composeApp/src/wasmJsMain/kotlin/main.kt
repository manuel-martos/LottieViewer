import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.mmartosdev.lottieviewer.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("LottieViewer") {
        App()
    }
}
