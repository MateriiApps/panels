import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.materiiapps.panels.StaticPanelsExample

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        StaticPanelsExample(false)
    }
}
