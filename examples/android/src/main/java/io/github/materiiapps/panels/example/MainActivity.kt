package io.github.materiiapps.panels.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import io.github.materiiapps.panels.SwipePanels
import io.github.materiiapps.panels.rememberSwipePanelsState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialTheme(
                colorScheme = when (isSystemInDarkTheme()) {
                    true -> darkColorScheme()
                    false -> lightColorScheme()
                }
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val state = rememberSwipePanelsState()
                    SwipePanels(
                        state = state,
                        start = {
                            SideContent(
                                title = "Start",
                                onClose = state::close
                            )
                        },
                        end = {
                            SideContent(
                                title = "End",
                                onClose = state::close
                            )
                        },
                        center = {
                            StyledScaffold(
                                topBar = {
                                    CenterAlignedTopAppBar(
                                        title = { Text("Center") },
                                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                                        )
                                    )
                                },
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(it),
                                    verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Button(onClick = state::openStart) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowLeft,
                                            contentDescription = null
                                        )
                                        Text("Open start")
                                    }
                                    Button(onClick = state::openEnd) {
                                        Text("Open end")
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        },
                        inBetweenPadding = 8.dp
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SideContent(
        title: String,
        onClose: () -> Unit
    ) {
        StyledScaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                )
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                FilledTonalButton(onClick = onClose) {
                    Text("Close")
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        }
    }

    @Composable
    fun StyledScaffold(
        topBar: @Composable () -> Unit,
        content: @Composable (PaddingValues) -> Unit
    ) {
        Scaffold(
            topBar = topBar,
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            content = content
        )
    }
}
