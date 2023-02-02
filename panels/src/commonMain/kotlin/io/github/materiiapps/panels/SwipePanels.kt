package io.github.materiiapps.panels

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

enum class SwipePanelValue {
    Start, Center, End
}

@Stable
class SwipePanelsState(initialValue: SwipePanelValue) {

    var value by mutableStateOf(initialValue, neverEqualPolicy())
        private set

    fun openStart() {
        value = SwipePanelValue.Start
    }

    fun openEnd() {
        value = SwipePanelValue.End
    }

    fun close() {
        value = SwipePanelValue.Center
    }
}

@Composable
fun rememberSwipePanelsState(
    initialValue: SwipePanelValue = SwipePanelValue.Center
): SwipePanelsState {
    return remember(initialValue) {
        SwipePanelsState(initialValue)
    }
}

@Composable
fun SwipePanels(
    start: @Composable () -> Unit,
    end: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    state: SwipePanelsState = rememberSwipePanelsState(),
    colors: PanelsColors = PanelsDefaults.colors(),
    shapes: PanelsShapes = PanelsDefaults.shapes(),
    paddings: PanelsPaddings = PanelsDefaults.paddings(),
    center: @Composable () -> Unit,
) {
    val density = LocalDensity.current

    var isDragging by remember { mutableStateOf(false) }
    var centerOffset by remember { mutableStateOf(0f) }
    var dragDelta by remember { mutableStateOf(0f) }
    var dragVelocity by remember { mutableStateOf(0f) }

    BoxWithConstraints(
        modifier = modifier
            .draggable(
                state = rememberDraggableState {
                    dragDelta = it
                },
                orientation = Orientation.Horizontal,
                onDragStarted = {
                    isDragging = true
                },
                onDragStopped = {
                    dragVelocity = it
                    println(it)
                    isDragging = false
                }
            )
    ) {
        val maxWidthFloat = remember(maxWidth, density) {
            with(density) {
                maxWidth.toPx()
            }
        }
        val maxWidthFraction = remember(maxWidthFloat) {
            maxWidthFloat * 0.8f
        }

        LaunchedEffect(isDragging) {
            if (!isDragging) {
                //TODO: not ideal
                val offsetWithVelocity = centerOffset + (dragVelocity / 20f)
                val maxWidthThird = maxWidthFloat / 3
                val target = when {
                    offsetWithVelocity <= -maxWidthThird -> -maxWidthFraction
                    offsetWithVelocity >= maxWidthThird -> maxWidthFraction
                    else -> 0f
                }
                Animatable(centerOffset).animateTo(target) {
                    centerOffset = this.value
                }
            }
        }
        LaunchedEffect(state.value) {
            val target = when (state.value) {
                SwipePanelValue.Center -> 0f
                SwipePanelValue.Start -> -maxWidthFraction
                SwipePanelValue.End -> maxWidthFraction
            }
            Animatable(centerOffset).animateTo(target) {
                centerOffset = this.value
            }
        }
        LaunchedEffect(dragDelta) {
            centerOffset = (centerOffset + dragDelta).coerceIn(
                minimumValue = -maxWidthFraction,
                maximumValue = maxWidthFraction
            )
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    alpha = if (centerOffset >= 0f) 1f else 0f
                }
                .align(Alignment.CenterStart)
                .zIndex(0f)
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .padding(paddings.starPanelPadding())
                .clip(shapes.startPanelShape())
                .background(colors.startPanelBackground()),
            propagateMinConstraints = true
        ) {
            start()
        }
        Box(
            modifier = Modifier
                .zIndex(1f)
                .offset { IntOffset(x = centerOffset.roundToInt(), y = 0) }
                .padding(paddings.centerPanelPadding())
                .clip(shapes.centerPanelShape())
                .background(colors.centerPanelBackground()),
            propagateMinConstraints = true
        ) {
            center()
        }
        Box(
            modifier = Modifier
                .graphicsLayer {
                    alpha = if (centerOffset <= 0f) 1f else 0f
                }
                .align(Alignment.CenterEnd)
                .zIndex(0f)
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .padding(paddings.endPanelPadding())
                .clip(shapes.endPanelShape())
                .background(colors.endPanelBackground()),
            propagateMinConstraints = true
        ) {
            end()
        }
    }
}