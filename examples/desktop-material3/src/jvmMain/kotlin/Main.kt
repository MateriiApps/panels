import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.materiiapps.panels.StaticPanelsExample

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme(
            colorScheme = when (isSystemInDarkTheme()) {
                true -> darkColorScheme()
                false -> lightColorScheme()
            },
        ) {
            Surface(color = MaterialTheme.colorScheme.background) {
                StaticPanelsExample(true)
            }
        }
    }
}
