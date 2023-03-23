package io.github.materiiapps.panels.material3.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.materiiapps.panels.PanelsDefaults
import io.github.materiiapps.panels.material3.M3SwipePanels

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = when (isSystemInDarkTheme()) {
                    true -> darkColorScheme()
                    false -> lightColorScheme()
                },
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    M3SwipePanels(
                        start = {
                            Box(Modifier.fillMaxSize())
                        },
                        end = {
                            Box(Modifier.fillMaxSize())
                        },
                        paddings = PanelsDefaults.paddings(
                            centerPanelPadding = PaddingValues(4.dp),
                            startPanelPadding = PaddingValues(8.dp),
                            endPanelPadding = PaddingValues(8.dp),
                        ),
                    ) {
                        Box(Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}
