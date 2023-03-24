package io.github.materiiapps.panels

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged

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
    colors: PanelsColors = PanelsDefaults.colors(),
    shapes: PanelsShapes = PanelsDefaults.shapes(),
    paddings: PanelsPaddings = PanelsDefaults.paddings(),
    metrics: StaticPanelsMetrics = PanelsDefaults.metrics(),
) {
    Row(modifier = modifier) {
        AnimatedVisibility(
            visible = state.startPanelValue == StaticPanelValue.Expanded,
            enter = slideInHorizontally { it * -2 },
            exit = slideOutHorizontally { -it },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(
                        min = metrics.startPanelMinWidth,
                        max = metrics.startPanelMaxWidth,
                    )
                    .padding(paddings.startPanelPadding)
                    .clip(shapes.startPanelShape)
                    .background(colors.startPanelBackground),
                propagateMinConstraints = true,
            ) {
                start()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(paddings.centerPanelPadding)
                .clip(shapes.centerPanelShape)
                .background(colors.centerPanelBackground)
                .animateContentSize(),
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
                    .widthIn(
                        min = metrics.endPanelMinWidth,
                        max = metrics.endPanelMaxWidth,
                    )
                    .padding(paddings.endPanelPadding)
                    .clip(shapes.endPanelShape)
                    .background(colors.endPanelBackground),
                propagateMinConstraints = true,
            ) {
                end()
            }
        }
    }
}
