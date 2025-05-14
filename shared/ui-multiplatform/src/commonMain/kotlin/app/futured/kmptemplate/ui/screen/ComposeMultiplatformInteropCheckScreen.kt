@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui.formScreen.FormScreen
import app.futured.kmptemplate.feature.ui.formScreen.FormViewState
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckScreen
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.ui.tools.SnackbarHostState
import dev.icerock.moko.resources.compose.stringResource


@Composable
fun ComposeMultiplatformInteropCheckScreen(
    screen: InteropCheckScreen,
    nativeComponent: @Composable (InteropCheckScreen, Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
    val snackbarState = remember { SnackbarHostState() }

    Content(viewState = viewState, actions = actions, modifier = modifier, snackbarState, {
        nativeComponent(screen, Modifier.weight(1f))
    })

}


@Composable
private fun Content(
    viewState: InteropCheckViewState,
    actions: InteropCheckScreen.Actions,
    modifier: Modifier = Modifier,
    snackbarState: SnackbarHostState,
    nativeComponent: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        nativeComponent()
        Button(onClick = { actions.onLaunchWebBrowser() }) {
            Text(text = stringResource(MR.strings.interop_check_launch_web))
        }
    }
}
