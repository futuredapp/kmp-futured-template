package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui.login.LoginScreen
import app.futured.kmptemplate.feature.ui.login.LoginViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource

@Composable
fun LoginScreenUi(
    screen: LoginScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: LoginViewState,
    actions: LoginScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = kmpStringResource(MR.strings.login_screen_text))
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = { actions.onLoginClick() }) {
            Text(text = "Login")
        }
    }
}
