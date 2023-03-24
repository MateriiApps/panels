package io.github.materiiapps.panels

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public object PanelsDefaults {
    @Composable
    public fun colors(
        startPanelBackground: Color = Color.LightGray,
        centerPanelBackground: Color = Color.White,
        endPanelBackground: Color = Color.LightGray
    ): PanelsColors {
        return PanelsColors(
            startPanelBackground = startPanelBackground,
            centerPanelBackground = centerPanelBackground,
            endPanelBackground = endPanelBackground,
        )
    }

    @Composable
    public fun paddings(
        startPanelPadding: PaddingValues = PaddingValues(0.dp),
        centerPanelPadding: PaddingValues = PaddingValues(0.dp),
        endPanelPadding: PaddingValues = PaddingValues(0.dp),
    ): PanelsPaddings {
        return PanelsPaddings(
            startPanelPadding = startPanelPadding,
            centerPanelPadding = centerPanelPadding,
            endPanelPadding = endPanelPadding,
        )
    }

    @Composable
    public fun shapes(
        startPanelShape: Shape = RectangleShape,
        centerPanelShape: Shape = RectangleShape,
        endPanelShape: Shape = RectangleShape,
    ): PanelsShapes {
        return PanelsShapes(
            startPanelShape = startPanelShape,
            centerPanelShape = centerPanelShape,
            endPanelShape = endPanelShape,
        )
    }

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

@Stable
public data class PanelsColors internal constructor(
    val startPanelBackground: Color,
    val centerPanelBackground: Color,
    val endPanelBackground: Color,
)

@Stable
public data class PanelsPaddings internal constructor(
    val startPanelPadding: PaddingValues,
    val centerPanelPadding: PaddingValues,
    val endPanelPadding: PaddingValues,
)

@Stable
public data class PanelsShapes internal constructor(
    val startPanelShape: Shape,
    val centerPanelShape: Shape,
    val endPanelShape: Shape
)

public data class StaticPanelsMetrics internal constructor(
    val startPanelMinWidth: Dp,
    val startPanelMaxWidth: Dp,
    val endPanelMinWidth: Dp,
    val endPanelMaxWidth: Dp,
)
