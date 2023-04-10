package io.github.materiiapps.panels

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
public data class StaticPanelsMetrics(
    val startPanelMinWidth: Dp = 72.dp,
    val startPanelMaxWidth: Dp = 256.dp,
    val endPanelMinWidth: Dp = 72.dp,
    val endPanelMaxWidth: Dp = 256.dp,
)

@Stable
public class StaticPanelsState(
    initialStartPanelExpanded: Boolean = true,
    initialEndPanelExpanded: Boolean = true,
) {
    public var isStartPanelExpanded: Boolean by mutableStateOf(initialStartPanelExpanded)
        internal set

    public var isEndPanelExpanded: Boolean by mutableStateOf(initialEndPanelExpanded)
        internal set

    public fun setStartPanelExpanded(expanded: Boolean) {
        isStartPanelExpanded = expanded
    }

    public fun expandStartPanel() {
        isStartPanelExpanded = true
    }

    public fun collapseStartPanel() {
        isStartPanelExpanded = false
    }

    public fun setEndPanel(expanded: Boolean) {
        isEndPanelExpanded = expanded
    }

    public fun expandEndPanel() {
        isEndPanelExpanded = true
    }

    public fun collapseEndPanel() {
        isEndPanelExpanded = false
    }
}

@Composable
public fun rememberStaticPanelsState(
    initialStartPanelExpanded: Boolean = true,
    initialEndPanelExpanded: Boolean = true,
): StaticPanelsState {
    return remember {
        StaticPanelsState(initialStartPanelExpanded, initialEndPanelExpanded)
    }
}

@Composable
public fun StaticPanels(
    start: @Composable () -> Unit,
    center: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    inBetweenPadding: Dp = 0.dp,
    state: StaticPanelsState = rememberStaticPanelsState(),
    metrics: StaticPanelsMetrics = StaticPanelsMetrics(),
) {
    Row(modifier = modifier) {
        if (state.isStartPanelExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = inBetweenPadding)
                    .widthIn(
                        min = metrics.startPanelMinWidth,
                        max = metrics.startPanelMaxWidth,
                    ),
                propagateMinConstraints = true,
            ) {
                start()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            propagateMinConstraints = true,
        ) {
            center()
        }

        if (state.isEndPanelExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = inBetweenPadding)
                    .widthIn(
                        min = metrics.endPanelMinWidth,
                        max = metrics.endPanelMaxWidth,
                    ),
                propagateMinConstraints = true,
            ) {
                end()
            }
        }
    }
}
