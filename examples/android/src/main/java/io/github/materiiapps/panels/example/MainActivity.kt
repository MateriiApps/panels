package io.github.materiiapps.panels.example

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.materiiapps.panels.PanelsDefaults
import io.github.materiiapps.panels.SwipePanels

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipePanels(
                start = {
                    Box(Modifier.fillMaxSize())
                },
                end = {
                    Box(Modifier.fillMaxSize())
                },
                paddings = PanelsDefaults.paddings(
                    startPanelPadding = PaddingValues(8.dp),
                    endPanelPadding = PaddingValues(8.dp),
                ),
                shapes = PanelsDefaults.shapes(
                    startPanelShape = RoundedCornerShape(12.dp),
                    endPanelShape = RoundedCornerShape(12.dp),
                    centerPanelShape = RoundedCornerShape(12.dp)
                )
            ) {
                Spacer(Modifier.fillMaxSize())
            }
        }
    }
}