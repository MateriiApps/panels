package io.github.materiiapps.panels.material3

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.github.materiiapps.panels.*

@Composable
public fun M3StaticPanels(
    start: @Composable () -> Unit,
    center: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    state: StaticPanelsState = rememberStaticPanelsState(),
    colors: PanelsColors = PanelsDefaults.material3Colors(),
    shapes: PanelsShapes = PanelsDefaults.material3Shapes(),
    paddings: PanelsPaddings = PanelsDefaults.paddings(),
    metrics: StaticPanelsMetrics = PanelsDefaults.metrics(),
) {
    StaticPanels(
        start = start,
        end = end,
        center = center,
        modifier = modifier,
        state = state,
        colors = colors,
        shapes = shapes,
        paddings = paddings,
        metrics = metrics,
    )
}

@Composable
public fun M3SwipePanels(
    start: @Composable () -> Unit,
    center: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    maxPanelWidth: Float = 0.9f,
    state: SwipePanelsState = rememberSwipePanelsState(),
    colors: PanelsColors = PanelsDefaults.material3Colors(),
    shapes: PanelsShapes = PanelsDefaults.material3Shapes(),
    paddings: PanelsPaddings = PanelsDefaults.paddings(),
) {
    SwipePanels(
        start = start,
        end = end,
        center = center,
        modifier = modifier,
        maxPanelWidth = maxPanelWidth,
        state = state,
        colors = colors,
        shapes = shapes,
        paddings = paddings,
    )
}

@Composable
public fun PanelsDefaults.material3Colors(
    centerPanelSurface: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
    startPanelSurface: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
    endPanelSurface: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
): PanelsColors {
    return colors(
        centerPanelBackground = centerPanelSurface,
        startPanelBackground = startPanelSurface,
        endPanelBackground = endPanelSurface,
    )
}

@Composable
public fun PanelsDefaults.material3Shapes(
    centerPanelShape: Shape = MaterialTheme.shapes.large,
    startPanelShape: Shape = MaterialTheme.shapes.medium,
    endPanelShape: Shape = MaterialTheme.shapes.medium,
): PanelsShapes {
    return shapes(
        centerPanelShape = centerPanelShape,
        startPanelShape = startPanelShape,
        endPanelShape = endPanelShape,
    )
}
