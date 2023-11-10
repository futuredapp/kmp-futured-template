package app.futured.kmpfuturedtemplate.android.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.feature.ui.login.LoginScreen
import app.futured.kmptemplate.feature.ui.login.LoginViewState

@Composable
fun LoginScreenUi(
    screen: LoginScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsState()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: LoginViewState,
    actions: LoginScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Hi from login")
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = { actions.onLoginClick() }) {
            Text(text = "Login")
        }
    }
}
