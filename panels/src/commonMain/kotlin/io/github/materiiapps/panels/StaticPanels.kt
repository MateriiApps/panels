package io.github.materiiapps.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class StaticPanelValue {
    Expanded,
    Collapsed,
}

@Stable
class StaticPanelsState(
    initialStartPanelValue: StaticPanelValue,
    initialEndPanelValue: StaticPanelValue
) {

    var startPanelValue by mutableStateOf(initialStartPanelValue)
        private set

    var endPanelValue by mutableStateOf(initialEndPanelValue)
        private set

    fun expandStartPanel() {
        startPanelValue = StaticPanelValue.Expanded
    }

    fun collapseStartPanel() {
        startPanelValue = StaticPanelValue.Collapsed
    }

    fun expandEndPanel() {
        endPanelValue = StaticPanelValue.Expanded
    }

    fun collapseEndPanel() {
        endPanelValue = StaticPanelValue.Collapsed
    }
}

@Composable
fun rememberStaticPanelsState(
    startPanelValue: StaticPanelValue = StaticPanelValue.Expanded,
    endPanelValue: StaticPanelValue = StaticPanelValue.Expanded,
): StaticPanelsState {
    return remember(startPanelValue, endPanelValue) {
        StaticPanelsState(startPanelValue, endPanelValue)
    }
}

@Composable
fun StaticPanels(
    start: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: PanelsColors = PanelsDefaults.colors(),
    shapes: PanelsShapes = PanelsDefaults.shapes(),
    paddings: PanelsPaddings = PanelsDefaults.paddings(),
    metrics: StaticPanelsMetrics = StaticPanelsDefaults.metrics(),
    panelState: StaticPanelsState = rememberStaticPanelsState(),
    center: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(shapes.containerShape())
            .background(colors.containerBackground()),
    ) {
        if (panelState.startPanelValue == StaticPanelValue.Expanded) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(
                        min = metrics.startPanelMinWidth(),
                        max = metrics.startPanelMaxWidth()
                    )
                    .padding(paddings.starPanelPadding())
                    .clip(shapes.startPanelShape())
                    .background(colors.startPanelBackground()),
                propagateMinConstraints = true
            ) {
                start()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(paddings.centerPanelPadding())
                .clip(shapes.centerPanelShape())
                .background(colors.centerPanelBackground()),
            propagateMinConstraints = true
        ) {
            center()
        }
        if (panelState.endPanelValue == StaticPanelValue.Expanded) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(
                        min = metrics.endPanelMinWidth(),
                        max = metrics.endPanelMaxWidth()
                    )
                    .padding(paddings.endPanelPadding())
                    .clip(shapes.endPanelShape())
                    .background(colors.endPanelBackground()),
                propagateMinConstraints = true
            ) {
                end()
            }
        }
    }
}

object StaticPanelsDefaults {

    @Composable
    fun metrics(
        startPanelMinWidth: Dp = 72.dp,
        startPanelMaxWidth: Dp = 256.dp,
        endPanelMinWidth: Dp = startPanelMinWidth,
        endPanelMaxWidth: Dp = startPanelMaxWidth,
    ): StaticPanelsMetrics {
        return StaticPanelsMetrics(
            startPanelMinWidth = startPanelMinWidth,
            startPanelMaxWidth = startPanelMaxWidth,
            endPanelMinWidth = endPanelMinWidth,
            endPanelMaxWidth = endPanelMaxWidth
        )
    }

}

data class StaticPanelsMetrics internal constructor(
    private val startPanelMinWidth: Dp,
    private val startPanelMaxWidth: Dp,
    private val endPanelMinWidth: Dp,
    private val endPanelMaxWidth: Dp,
) {

    @Composable
    internal fun startPanelMinWidth(): Dp {
        return startPanelMinWidth
    }
    @Composable
    internal fun startPanelMaxWidth(): Dp {
        return startPanelMaxWidth
    }
    @Composable
    internal fun endPanelMinWidth(): Dp {
        return endPanelMinWidth
    }
    @Composable
    internal fun endPanelMaxWidth(): Dp {
        return endPanelMaxWidth
    }

}