package app.futured.kmptemplate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen

fun ComposeMultiplatformFirstScreenController(screen: FirstScreen) = ComposeUIViewController {
    MyApplicationTheme {
        ComposeMultiplatformFirstScreen(screen, Modifier.fillMaxSize())
    }
}
