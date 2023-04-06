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
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.materiiapps.panels.StaticPanels
import io.github.materiiapps.panels.rememberStaticPanelsState

@OptIn(ExperimentalMaterial3Api::class)
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme(
            colorScheme = when (isSystemInDarkTheme()) {
                true -> darkColorScheme()
                false -> lightColorScheme()
            }
        ) {
            Surface(color = MaterialTheme.colorScheme.background) {
                val state = rememberStaticPanelsState()
                StaticPanels(
                    state = state,
                    start = {
                        SideContent(
                            title = "Start",
                            onClose = state::collapseStartPanel
                        )
                    },
                    end = {
                        SideContent(
                            title = "End",
                            onClose = state::collapseEndPanel
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
                                Button(onClick = state::expandStartPanel) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowLeft,
                                        contentDescription = null
                                    )
                                    Text("Open start")
                                }
                                Button(onClick = state::expandEndPanel) {
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
                colors = TopAppBarDefaults.smallTopAppBarColors(
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

@OptIn(ExperimentalMaterial3Api::class)
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