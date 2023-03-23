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
fun M3StaticPanels(
    start: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: PanelsColors = PanelsDefaults.material3Colors(),
    shapes: PanelsShapes = PanelsDefaults.material3Shapes(),
    paddings: PanelsPaddings = PanelsDefaults.paddings(),
    metrics: StaticPanelsMetrics = StaticPanelsDefaults.metrics(),
    state: StaticPanelsState = rememberStaticPanelsState(),
    center: @Composable () -> Unit,
) {
    StaticPanels(
        modifier = modifier,
        colors = colors,
        shapes = shapes,
        paddings = paddings,
        metrics = metrics,
        state = state,
        start = start,
        end = end,
        center = center,
    )
}

@Composable
fun M3SwipePanels(
    start: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: PanelsColors = PanelsDefaults.material3Colors(),
    shapes: PanelsShapes = PanelsDefaults.material3Shapes(),
    paddings: PanelsPaddings = PanelsDefaults.paddings(),
    state: SwipePanelsState = rememberSwipePanelsState(),
    center: @Composable () -> Unit,
) {
    SwipePanels(
        modifier = modifier,
        colors = colors,
        shapes = shapes,
        paddings = paddings,
        state = state,
        start = start,
        end = end,
        center = center,
    )
}

@Composable
fun PanelsDefaults.material3Colors(
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
fun PanelsDefaults.material3Shapes(
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
