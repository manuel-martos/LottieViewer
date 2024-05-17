import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.ui.platform.findComposeDefaultViewModelStoreOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.mmartosdev.lottieviewer.App
import java.awt.Dimension

@OptIn(InternalComposeApi::class)
fun main() = application {
    Window(
        title = "LottieViewer",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        CompositionLocalProvider(LocalViewModelStoreOwner provides findComposeDefaultViewModelStoreOwner()!!) {
            App()
        }
    }
}