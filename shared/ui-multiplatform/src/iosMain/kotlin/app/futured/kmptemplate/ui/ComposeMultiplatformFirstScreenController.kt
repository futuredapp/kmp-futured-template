package app.futured.kmptemplate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.ui.screen.ComposeMultiplatformFirstScreen

fun ComposeMultiplatformFirstScreenController(screen: FirstScreen) = ComposeUIViewController {
    MyApplicationTheme {
        ComposeMultiplatformFirstScreen(screen, Modifier.fillMaxSize())
    }
}
