package io.github.materiiapps.panels.material3.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import io.github.materiiapps.panels.SwipingPanelsExample

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
                    SwipingPanelsExample(true)
                }
            }
        }
    }
}
