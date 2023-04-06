package io.github.materiiapps.panels

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public enum class StaticPanelValue {
    Expanded,
    Collapsed,
}

@Stable
public class StaticPanelsState(
    initialStartPanelValue: StaticPanelValue,
    initialEndPanelValue: StaticPanelValue
) {
    public var startPanelValue: StaticPanelValue by mutableStateOf(initialStartPanelValue)
        internal set

    public var endPanelValue: StaticPanelValue by mutableStateOf(initialEndPanelValue)
        internal set

    public fun setStartPanel(value: StaticPanelValue) {
        startPanelValue = value
    }

    public fun expandStartPanel() {
        startPanelValue = StaticPanelValue.Expanded
    }

    public fun collapseStartPanel() {
        startPanelValue = StaticPanelValue.Collapsed
    }

    public fun setEndPanel(value: StaticPanelValue) {
        endPanelValue = value
    }

    public fun expandEndPanel() {
        endPanelValue = StaticPanelValue.Expanded
    }

    public fun collapseEndPanel() {
        endPanelValue = StaticPanelValue.Collapsed
    }
}

@Composable
public fun rememberStaticPanelsState(
    initialStartPanelValue: StaticPanelValue = StaticPanelValue.Expanded,
    initialEndPanelValue: StaticPanelValue = StaticPanelValue.Expanded,
): StaticPanelsState {
    return remember {
        StaticPanelsState(initialStartPanelValue, initialEndPanelValue)
    }
}

@Composable
public fun StaticPanels(
    start: @Composable () -> Unit,
    center: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    state: StaticPanelsState = rememberStaticPanelsState(),
    containerColor: Color = Color.Unspecified,
    metrics: StaticPanelsMetrics = PanelsDefaults.metrics(),
    inBetweenPadding: Dp = 0.dp,
) {
    Row(modifier = modifier.background(containerColor)) {
        AnimatedVisibility(
            visible = state.startPanelValue == StaticPanelValue.Expanded,
            enter = slideInHorizontally { it * -2 },
            exit = slideOutHorizontally { -it },
        ) {
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

        AnimatedVisibility(
            visible = state.endPanelValue == StaticPanelValue.Expanded,
            enter = slideInHorizontally { it * 2 },
            exit = slideOutHorizontally { it },
        ) {
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
