package app.futured.kmpfuturedtemplate.android.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.feature.ui.login.LoginScreen
import app.futured.kmptemplate.feature.ui.login.LoginViewState
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun LoginScreenUi(
    screen: LoginScreen,
    modifier: Modifier = Modifier
) {
    val actions = screen.actions
    val viewState by screen.viewState.subscribeAsState()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: LoginViewState,
    actions: LoginScreen.Actions,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Hi from login")
            OutlinedButton(onClick = actions::onNavigateHome) {
                Text(text = "Home")
            }
        }
    }
}
