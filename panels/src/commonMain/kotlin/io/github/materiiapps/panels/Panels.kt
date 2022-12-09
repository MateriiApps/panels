package io.github.materiiapps.panels

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

typealias ComposeAlignment = androidx.compose.ui.Alignment

enum class PanelValue {
    Expanded, Collapsed
}

enum class Alignment {
    Start, Center, End
}

@Stable
class PanelsState(
    defaultAlignment: Alignment,
    startPanelValue: PanelValue,
    endPanelValue: PanelValue,
) {

    var alignment by mutableStateOf(defaultAlignment)
        private set

    var startPanelValue by mutableStateOf(startPanelValue)
        private set

    var endPanelValue by mutableStateOf(endPanelValue)
        private set

    fun collapseStartPanel() {
        startPanelValue = PanelValue.Collapsed
    }

    fun expandStartPanel() {
        startPanelValue = PanelValue.Expanded
    }

    fun collapseEndPanel() {
        endPanelValue = PanelValue.Collapsed
    }

    fun expandEndPanel() {
        endPanelValue = PanelValue.Expanded
    }

    fun alignStart() {
        alignment = Alignment.Start
    }

    fun alignCenter() {
        alignment = Alignment.Center
    }

    fun alignEnd() {
        alignment = Alignment.End
    }
}

@Composable
fun rememberPanelsState(
    defaultAlignment: Alignment = Alignment.Center,
    startPanelValue: PanelValue = PanelValue.Expanded,
    endPanelValue: PanelValue = PanelValue.Expanded,
): PanelsState {
    return remember(defaultAlignment, startPanelValue, endPanelValue) {
        PanelsState(defaultAlignment, startPanelValue, endPanelValue)
    }
}

@Composable
fun Panels(
    state: PanelsState = rememberPanelsState(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    startPanel: @Composable () -> Unit,
    endPanel: @Composable () -> Unit,
    centerPanel: @Composable () -> Unit,
) {
    BoxWithConstraints(modifier) {
        if (maxWidth >= 840.dp) {
            StaticPanels(state, modifier, startPanel, endPanel, centerPanel)
        } else {
            SwipingPanels(state, modifier, enabled, startPanel, endPanel, centerPanel)
        }
    }
}

@Composable
private fun SwipingPanels(
    state: PanelsState,
    modifier: Modifier,
    enabled: Boolean,
    startPanel: @Composable () -> Unit,
    endPanel: @Composable () -> Unit,
    centerPanel: @Composable () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    val centerPanelOffset = remember { androidx.compose.animation.core.Animatable(0f) }
    var isDragging by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val draggableState = rememberDraggableState {
        coroutineScope.launch {
            centerPanelOffset.snapTo(centerPanelOffset.value + it)
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .draggable(
                state = draggableState,
                orientation = Orientation.Horizontal,
                enabled = enabled,
                onDragStopped = {
                    isDragging = false
                },
                onDragStarted = {
                    isDragging = true
                }
            )
    ) {
        LaunchedEffect(isDragging) {
            if (!isDragging) {
                val shouldAlignStart = centerPanelOffset.value >= maxWidth.value / 2
                val shouldAlignEnd = centerPanelOffset.value <= (-maxWidth.value) / 2

                val offset = (maxWidth.value * 0.9f) - 32f

                when {
                    shouldAlignStart -> {
                        state.alignStart()
                        centerPanelOffset.animateTo(offset)
                    }
                    shouldAlignEnd -> {
                        state.alignEnd()
                        centerPanelOffset.animateTo(-offset)
                    }
                    else -> {
                        state.alignCenter()
                        centerPanelOffset.animateTo(0f)
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .offset { IntOffset(x = centerPanelOffset.value.roundToInt(), y = 0) }
        ) {
            centerPanel()
        }
        Box(
            modifier = Modifier
                .align(ComposeAlignment.CenterStart)
                .fillMaxWidth(0.9f)
                .zIndex(
                    if ((layoutDirection == LayoutDirection.Ltr && state.alignment == Alignment.Start) ||
                        (layoutDirection == LayoutDirection.Rtl && state.alignment == Alignment.End)) {
                        1f
                    } else 0f
                )
        ) {
           startPanel()
        }
        Box(
            modifier = Modifier
                .align(ComposeAlignment.CenterEnd)
                .padding(start = 12.dp)
                .fillMaxWidth(0.9f)
                .zIndex(
                    if ((layoutDirection == LayoutDirection.Ltr && state.alignment == Alignment.End) ||
                        (layoutDirection == LayoutDirection.Rtl && state.alignment == Alignment.Start)) {
                        1f
                    } else 0f
                )
        ) {
           endPanel()
        }
    }
}

@Composable
private fun StaticPanels(
    state: PanelsState,
    modifier: Modifier,
    startPanel: @Composable () -> Unit,
    endPanel: @Composable () -> Unit,
    centerPanel: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        if (state.startPanelValue == PanelValue.Expanded) {
            Box(Modifier.widthIn(min = 48.dp, max = 256.dp).fillMaxHeight()) {
                startPanel()
            }
        }
        Box(Modifier.fillMaxHeight().weight(1f)) {
            centerPanel()
        }
        if (state.endPanelValue == PanelValue.Expanded) {
            Box(Modifier.widthIn(min = 48.dp, max = 256.dp).fillMaxHeight()) {
                endPanel()
            }
        }
    }
}