package io.github.materiiapps.panels

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.materiiapps.panels.material3.M3StaticPanels

@Composable
fun StaticPanelsExample(isM3: Boolean) {
    val state = rememberStaticPanelsState()

    if (isM3) {
        M3StaticPanels(
            start = { Start() },
            center = { Center(state) },
            end = { End() },
            state = state,
            paddings = PanelsDefaults.paddings(
                startPanelPadding = PaddingValues(12.dp),
                centerPanelPadding = PaddingValues(8.dp),
                endPanelPadding = PaddingValues(12.dp),
            ),
        )

    } else {
        StaticPanels(
            start = { Start() },
            center = { Center(state) },
            end = { End() },
            state = state,
        )
    }
}

@Composable
private fun Start() {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "Start Panel",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun Center(state: StaticPanelsState) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        val (startText, startOnClick) = if (state.startPanelValue == StaticPanelValue.Expanded) {
            "Close Start Panel" to state::collapseStartPanel
        } else {
            "Open Start Panel" to state::expandStartPanel
        }

        Button(onClick = startOnClick) {
            Text(startText)
        }

        val (endText, endOnClick) = if (state.endPanelValue == StaticPanelValue.Expanded) {
            "Close End Panel" to state::collapseEndPanel
        } else {
            "Open End Panel" to state::expandEndPanel
        }
        Button(onClick = endOnClick) {
            Text(endText)
        }
    }
}

@Composable
private fun End() {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "End Panel",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
