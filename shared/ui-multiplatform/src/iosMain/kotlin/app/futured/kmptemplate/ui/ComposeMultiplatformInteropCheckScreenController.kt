package app.futured.kmptemplate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import androidx.compose.ui.window.ComposeUIViewController
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckScreen
import app.futured.kmptemplate.ui.screen.ComposeMultiplatformInteropCheckScreen
import platform.UIKit.UIViewController

fun ComposeMultiplatformInteropCheckScreenController(screen: InteropCheckScreen, nativePart: ()->UIViewController) = ComposeUIViewController {
    MyApplicationTheme {
        ComposeMultiplatformInteropCheckScreen(screen, { screen, modifier ->
            UIKitViewController(
                modifier = modifier.fillMaxSize(),
                factory = nativePart,
                update = {}
            )
        }, Modifier.fillMaxSize().navigationBarsPadding().imePadding())
    }
}
