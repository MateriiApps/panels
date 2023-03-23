package io.github.materiiapps.panels

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object PanelsDefaults {

    @Composable
    fun colors(
        centerPanelBackground: Color = Color.White,
        startPanelBackground: Color = Color.LightGray,
        endPanelBackground: Color = Color.LightGray
    ): PanelsColors {
        return PanelsColors(
            centerPanelBackground = centerPanelBackground,
            startPanelBackground = startPanelBackground,
            endPanelBackground = endPanelBackground,
        )
    }

    @Composable
    fun paddings(
        centerPanelPadding: PaddingValues = PaddingValues(0.dp),
        startPanelPadding: PaddingValues = PaddingValues(0.dp),
        endPanelPadding: PaddingValues = PaddingValues(0.dp),
    ): PanelsPaddings {
        return PanelsPaddings(
            centerPanelPadding = centerPanelPadding,
            startPanelPadding = startPanelPadding,
            endPanelPadding = endPanelPadding,
        )
    }

    @Composable
    fun shapes(
        centerPanelShape: Shape = RectangleShape,
        startPanelShape: Shape = RectangleShape,
        endPanelShape: Shape = RectangleShape,
    ): PanelsShapes {
        return PanelsShapes(
            centerPanelShape = centerPanelShape,
            startPanelShape = startPanelShape,
            endPanelShape = endPanelShape,
        )
    }
}

@Stable
data class PanelsColors internal constructor(
    private val centerPanelBackground: Color,
    private val startPanelBackground: Color,
    private val endPanelBackground: Color,
) {

    @Composable
    internal fun centerPanelBackground(): Color {
        return centerPanelBackground
    }

    @Composable
    internal fun startPanelBackground(): Color {
        return startPanelBackground
    }

    @Composable
    internal fun endPanelBackground(): Color {
        return endPanelBackground
    }
}

@Stable
data class PanelsPaddings internal constructor(
    private val centerPanelPadding: PaddingValues,
    private val startPanelPadding: PaddingValues,
    private val endPanelPadding: PaddingValues,
) {

    @Composable
    internal fun starPanelPadding(): PaddingValues {
        return startPanelPadding
    }

    @Composable
    internal fun centerPanelPadding(): PaddingValues {
        return centerPanelPadding
    }

    @Composable
    internal fun endPanelPadding(): PaddingValues {
        return endPanelPadding
    }
}

@Stable
data class PanelsShapes internal constructor(
    private val centerPanelShape: Shape,
    private val startPanelShape: Shape,
    private val endPanelShape: Shape
) {

    @Composable
    internal fun centerPanelShape(): Shape {
        return centerPanelShape
    }

    @Composable
    internal fun startPanelShape(): Shape {
        return startPanelShape
    }

    @Composable
    internal fun endPanelShape(): Shape {
        return endPanelShape
    }
}
