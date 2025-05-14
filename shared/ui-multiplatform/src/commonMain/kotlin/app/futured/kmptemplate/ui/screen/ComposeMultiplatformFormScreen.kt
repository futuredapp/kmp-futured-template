@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui.formScreen.FormScreen
import app.futured.kmptemplate.feature.ui.formScreen.FormViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.ui.tools.SnackbarHostState
import dev.icerock.moko.resources.compose.stringResource


@Composable
fun ComposeMultiplatformFormScreen(
    screen: FormScreen,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
    val snackbarState = remember { SnackbarHostState() }

    Content(viewState = viewState, actions = actions, modifier = modifier, snackbarState)

}


@Composable
private fun Content(
    viewState: FormViewState,
    actions: FormScreen.Actions,
    modifier: Modifier = Modifier,
    snackbarState: SnackbarHostState,
) {
    Column(
        modifier = modifier,
    ) {

        CenterAlignedTopAppBar(
            title = { Text(stringResource(MR.strings.form_screen_title)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(200.dp))

            TextField(
                value = viewState.firstName,
                onValueChange = actions::onFirstNameChange,
                label = {
                    Text(
                        text = stringResource(MR.strings.form_text_field_label_first_name),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp),
            )

            TextField(
                value = viewState.surname,
                onValueChange = actions::onSurnameChange,
                label = {
                    Text(
                        text = stringResource(MR.strings.form_text_field_label_surname),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp),
            )

            TextField(
                value = viewState.email,
                onValueChange = actions::onEmailChange,
                label = {
                    Text(
                        text = stringResource(MR.strings.form_text_field_label_email),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp),
            )

            TextField(
                value = viewState.phone,
                onValueChange = actions::onPhoneChange,
                label = {
                    Text(
                        text = stringResource(MR.strings.form_text_field_label_phone),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp),
            )

            TextField(
                value = viewState.password,
                onValueChange = actions::onPasswordChange,
                label = {
                    Text(
                        text = stringResource(MR.strings.form_text_field_label_password),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp),
            )

            Button(onClick = { actions.onNext() }) {
                Text(text = stringResource(MR.strings.form_button_interop_screen))
            }

        }
    }
}
