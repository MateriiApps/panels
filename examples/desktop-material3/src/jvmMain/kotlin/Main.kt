import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.materiiapps.panels.PanelsDefaults
import io.github.materiiapps.panels.material3.M3StaticPanels

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme(
            colorScheme = when (isSystemInDarkTheme()) {
                true -> darkColorScheme()
                false -> lightColorScheme()
            }
        ) {
            Surface(color = MaterialTheme.colorScheme.background) {
                M3StaticPanels(
                    start = {
                        Box(Modifier.fillMaxWidth())
                    },
                    end = {
                        Box(Modifier.fillMaxWidth())
                    },
                    paddings = PanelsDefaults.paddings(
                        centerPanelPadding = PaddingValues(8.dp),
                        startPanelPadding = PaddingValues(12.dp),
                        endPanelPadding = PaddingValues(12.dp),
                    ),
                ) {
                    Box(Modifier.fillMaxWidth())
                }
            }
        }
    }
}
