package io.github.materiiapps.panels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.materiiapps.panels.material3.M3SwipePanels

@Composable
fun SwipingPanelsExample(isM3: Boolean) {
    val state = rememberSwipePanelsState()

    if (isM3) {
        M3SwipePanels(
            state = state,
            start = { Start(state) },
            center = { Center(state) },
            end = { End(state) },
            paddings = PanelsDefaults.paddings(
                startPanelPadding = PaddingValues(12.dp),
                centerPanelPadding = PaddingValues(8.dp),
                endPanelPadding = PaddingValues(12.dp),
            ),
        )

    } else {
        SwipePanels(
            state = state,
            start = { Start(state) },
            center = { Center(state) },
            end = { End(state) },
        )
    }
}

@Composable
private fun Start(state: SwipePanelsState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Start Panel")
        Button(
            onClick = state::close,
        ) {
            Text("Close Panel")
        }
    }
}

@Composable
private fun Center(state: SwipePanelsState) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            onClick = state::openStart,
        ) {
            Text("Open Start Panel")
        }
        Button(
            onClick = state::openEnd,
        ) {
            Text("Open End Panel")
        }
    }
}

@Composable
private fun End(state: SwipePanelsState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("End Panel")
        Button(
            onClick = state::close,
        ) {
            Text("Close Panel")
        }
    }
}
