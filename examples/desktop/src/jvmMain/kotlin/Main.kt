import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.materiiapps.panels.PanelsDefaults
import io.github.materiiapps.panels.StaticPanels

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        StaticPanels(
            start = {
                Box(Modifier.fillMaxWidth())
            },
            end = {
                Box(Modifier.fillMaxWidth())
            },
            paddings = PanelsDefaults.paddings(
                startPanelPadding = PaddingValues(end = 8.dp),
                endPanelPadding = PaddingValues(start = 8.dp),
            ),
            shapes = PanelsDefaults.shapes(
                startPanelShape = RoundedCornerShape(
                    topEnd = 12.dp,
                    bottomEnd = 12.dp
                ),
                endPanelShape = RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp,
                ),
                centerPanelShape = RoundedCornerShape(8.dp)
            )
        ) {
            Box(Modifier.fillMaxWidth())
        }
    }
}
