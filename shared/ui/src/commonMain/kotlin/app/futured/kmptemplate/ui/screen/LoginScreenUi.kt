package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.feature.ui.loginScreen.LoginScreen
import app.futured.kmptemplate.feature.ui.loginScreen.LoginScreen.Actions.Companion.noOpActions
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.ui.components.Showcase
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun LoginScreenUi(
    screen: LoginScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions

    Content(actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    actions: LoginScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(MR.strings.login_screen_title),
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { actions.onLoginClick() }) {
            Text(text = stringResource(MR.strings.generic_sign_in))
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() = Showcase {
    Surface {
        Content(
            actions = noOpActions(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
