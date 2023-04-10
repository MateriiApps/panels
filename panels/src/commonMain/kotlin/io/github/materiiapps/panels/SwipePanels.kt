package io.github.materiiapps.panels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

public enum class SwipePanelsValue {
    Start,
    Center,
    End,
}

@Stable
public class SwipePanelsState(
    initialValue: SwipePanelsValue = SwipePanelsValue.Center,
) {
    public var currentValue: SwipePanelsValue by mutableStateOf(initialValue, referentialEqualityPolicy())
        internal set

    // neverEqualPolicy is needed in order to reanimate to the same values
    public var targetValue: SwipePanelsValue by mutableStateOf(initialValue, neverEqualPolicy())
        internal set

    public var isDragging: Boolean by mutableStateOf(false)
        internal set

    public fun setValue(value: SwipePanelsValue) {
        targetValue = value
    }

    public fun openStart() {
        targetValue = SwipePanelsValue.Start
    }

    public fun openEnd() {
        targetValue = SwipePanelsValue.End
    }

    public fun close() {
        targetValue = SwipePanelsValue.Center
    }
}

@Composable
public fun rememberSwipePanelsState(
    initialValue: SwipePanelsValue = SwipePanelsValue.Center,
): SwipePanelsState {
    return remember {
        SwipePanelsState(initialValue)
    }
}

@Composable
public fun SwipePanels(
    start: @Composable () -> Unit,
    center: @Composable () -> Unit,
    end: @Composable () -> Unit,
    containerColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier,
    maxPanelWidth: Float = 0.9f,
    changeThreshold: Float = 0.1f,
    inBetweenPadding: Dp = 0.dp,
    state: SwipePanelsState = rememberSwipePanelsState(),
) {
    val density = LocalDensity.current

    var dragVelocity by remember { mutableStateOf(0f) }
    var maxWidthSynthetic by remember { mutableStateOf(0f) }
    var centerOffset by remember { mutableStateOf(0f) }

    LaunchedEffect(state.isDragging, maxWidthSynthetic) {
        if (!state.isDragging) {
            val offsetWithVelocity = centerOffset + (dragVelocity / 26f)
            val inverseThreshold = 1 - changeThreshold

            val targetValue = when {
                state.targetValue != SwipePanelsValue.Start && offsetWithVelocity >= maxWidthSynthetic * changeThreshold ->
                    SwipePanelsValue.Start

                state.targetValue != SwipePanelsValue.End && offsetWithVelocity <= -maxWidthSynthetic * changeThreshold ->
                    SwipePanelsValue.End

                state.targetValue == SwipePanelsValue.Start && offsetWithVelocity <= maxWidthSynthetic * inverseThreshold ->
                    SwipePanelsValue.Center
                state.targetValue == SwipePanelsValue.Start ->
                    SwipePanelsValue.Start

                state.targetValue == SwipePanelsValue.End && offsetWithVelocity >= -maxWidthSynthetic * inverseThreshold ->
                    SwipePanelsValue.Center
                state.targetValue == SwipePanelsValue.End
                -> SwipePanelsValue.End

                state.targetValue == SwipePanelsValue.Center ->
                    SwipePanelsValue.Center

                else -> state.currentValue
            }

            state.targetValue = targetValue
        }
    }

    // Animate the panels when targetValue changes or cancel on drag
    LaunchedEffect(state.targetValue, state.isDragging) {
        if (state.isDragging) {
            // Cancel the previous LaunchedEffect
            return@LaunchedEffect
        }

        val targetValue = when (state.targetValue) {
            SwipePanelsValue.Center -> 0f
            SwipePanelsValue.Start -> maxWidthSynthetic
            SwipePanelsValue.End -> -maxWidthSynthetic
        }
        Animatable(centerOffset).animateTo(
            targetValue = targetValue,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        ) {
            centerOffset = this.value
        }

        state.currentValue = state.targetValue
    }

    BoxWithConstraints(
        modifier = modifier
            .background(containerColor)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    centerOffset = (centerOffset + delta).coerceIn(
                        minimumValue = -maxWidthSynthetic,
                        maximumValue = maxWidthSynthetic,
                    )
                },
                onDragStarted = {
                    dragVelocity = 0f
                    state.isDragging = true
                },
                onDragStopped = {
                    dragVelocity = it
                    state.isDragging = false
                },
            ),
    ) {
        LaunchedEffect(maxWidth, maxPanelWidth, density) {
            maxWidthSynthetic = maxPanelWidth *
                    density.run { maxWidth.toPx() } +
                    density.run { inBetweenPadding.toPx() }
        }

        val startVisible by remember { derivedStateOf { centerOffset >= 0 } }
        val endVisible by remember { derivedStateOf { centerOffset <= 0 } }

        // Panels
        if (startVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .zIndex(0f)
                    .fillMaxHeight()
                    .fillMaxWidth(maxPanelWidth),
                propagateMinConstraints = true,
            ) {
                start()
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(x = centerOffset.roundToInt(), y = 0) }
                .zIndex(1f),
            propagateMinConstraints = true,
        ) {
            center()
        }

        if (endVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .zIndex(0f)
                    .fillMaxHeight()
                    .fillMaxWidth(maxPanelWidth),
                propagateMinConstraints = true,
            ) {
                end()
            }
        }
    }
}
