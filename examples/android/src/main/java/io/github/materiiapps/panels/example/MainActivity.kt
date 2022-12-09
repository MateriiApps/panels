package io.github.materiiapps.panels.example

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.materiiapps.panels.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Panels(
                startPanel = {
                    Box(Modifier.fillMaxSize().background(Color.Blue))
                },
                endPanel = {
                    Box(Modifier.fillMaxSize().background(Color.Magenta))
                }
            ) {
                Box(Modifier.fillMaxSize().background(Color.White))
            }
        }
    }
}