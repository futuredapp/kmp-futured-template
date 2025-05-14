package app.futured.kmptemplate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import app.futured.kmptemplate.feature.ui.formScreen.FormScreen
import app.futured.kmptemplate.ui.screen.ComposeMultiplatformFormScreen

fun ComposeMultiplatformFormScreenController(screen: FormScreen) = ComposeUIViewController {
    MyApplicationTheme {
        ComposeMultiplatformFormScreen(screen, Modifier.fillMaxSize().navigationBarsPadding().imePadding())
    }
}
