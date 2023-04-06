package io.github.materiiapps.panels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public object PanelsDefaults {

    @Composable
    public fun metrics(
        startPanelMinWidth: Dp = 72.dp,
        startPanelMaxWidth: Dp = 256.dp,
        endPanelMinWidth: Dp = startPanelMinWidth,
        endPanelMaxWidth: Dp = startPanelMaxWidth,
    ): StaticPanelsMetrics {
        return StaticPanelsMetrics(
            startPanelMinWidth = startPanelMinWidth,
            startPanelMaxWidth = startPanelMaxWidth,
            endPanelMinWidth = endPanelMinWidth,
            endPanelMaxWidth = endPanelMaxWidth,
        )
    }
}

@Immutable
public data class StaticPanelsMetrics internal constructor(
    val startPanelMinWidth: Dp,
    val startPanelMaxWidth: Dp,
    val endPanelMinWidth: Dp,
    val endPanelMaxWidth: Dp,
)
