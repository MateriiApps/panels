// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.materiiapps.panels.Panels

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
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
