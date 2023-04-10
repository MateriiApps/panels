package io.github.materiiapps.panels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

/**
 * Used for keeping track of which panel is currently active.
 */
public enum class SwipePanelsValue {
    /**
     * The first panel, showing it and a portion of the center panel on the right.
     */
    Start,

    /**
     * The middle panel, taking up the full screen.
     * Both other panels are not rendered when this is active.
     */
    Center,

    /**
     * The last (third) panel, showing it and a portion of the center panel on the left.
     */
    End,
}

@Stable
public class SwipePanelsState(
    initialValue: SwipePanelsValue = SwipePanelsValue.Center,
) {
    /**
     * The current panels state excluding dragging
     */
    public var currentValue: SwipePanelsValue by mutableStateOf(initialValue, referentialEqualityPolicy())
        internal set

    // neverEqualPolicy is needed in order to reanimate to the same values
    public var targetValue: SwipePanelsValue by mutableStateOf(initialValue, neverEqualPolicy())
        internal set

    /**
     * Whether any of the panels are currently being dragged.
     * This does not affect [currentValue] or [targetValue].
     */
    public var isDragging: Boolean by mutableStateOf(false)
        internal set

    /**
     * Animates towards a target panels state.
     */
    public fun setValue(value: SwipePanelsValue) {
        targetValue = value
    }

    /**
     * Animates to opening the start panel.
     */
    public fun openStart() {
        targetValue = SwipePanelsValue.Start
    }

    /**
     * Animates to opening the end panel.
     */
    public fun openEnd() {
        targetValue = SwipePanelsValue.End
    }

    /**
     * Animates to closing both the start & end panels, returning to the center.
     */
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

/**
 * Makes a 3-panel Discord-like layout.
 *
 * **NOTE**: This does NOT fill max size! You most likely want to apply a max size modifier
 * otherwise the panels will be as tiny as your panel content.
 *
 * @param start The start (first) panel
 * @param center The full middle panel
 * @param end The end (third) panel
 * @param modifier The modifier applied to the base layout
 * @param maxPanelWidth Fraction of how much width the start/end panels use up before the center panel appears on the side.
 * @param changeThreshold Fraction of how much width of the base layout needs to be dragged before the panels switch to the new state.
 * @param inBetweenPadding Padding between the start/center and center/end panel areas.
 * @param state Panels state. Look at [SwipePanelsState] and [rememberSwipePanelsState] for more information.
 */
@Composable
public fun SwipePanels(
    start: @Composable () -> Unit,
    center: @Composable () -> Unit,
    end: @Composable () -> Unit,
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

    // Update targetValue when dragging stops
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
        // Keep track of the max width available in the current layout accounting for our constraints
        LaunchedEffect(maxWidth, maxPanelWidth, density) {
            maxWidthSynthetic = maxPanelWidth *
                    density.run { maxWidth.toPx() } +
                    density.run { inBetweenPadding.toPx() }
        }

        // Panels are not rendered if they are not visible
        val startVisible by remember { derivedStateOf { centerOffset > 0 } }
        val endVisible by remember { derivedStateOf { centerOffset < 0 } }

        // Start panel
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

        // Center panel
        Box(
            modifier = Modifier
                .offset { IntOffset(x = centerOffset.roundToInt(), y = 0) }
                .zIndex(1f),
            propagateMinConstraints = true,
        ) {
            center()
        }

        // End panel
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
