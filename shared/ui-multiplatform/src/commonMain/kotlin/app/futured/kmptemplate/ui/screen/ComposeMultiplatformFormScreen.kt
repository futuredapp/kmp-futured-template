 @file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui.formScreen.FormScreen
import app.futured.kmptemplate.feature.ui.formScreen.FormViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.ui.MyApplicationTheme
import app.futured.kmptemplate.ui.tools.SnackbarHostState
import app.futured.kmptemplate.ui.widgets.Alert
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

 @Composable
fun ComposeMultiplatformFormScreen(
    screen: FormScreen,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
    val snackbarState = remember { SnackbarHostState() }

    viewState.alertDialogUi?.also {
        Alert(it)
    }
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


            LazyRow(
                modifier.semantics(true) {
                    contentDescription = "Seznam obrázků"
                },
            ) {
                items((0..10).toList()) {
                    Image(
                        painter = painterResource(MR.images.illus_card),
                        contentDescription = "index $it",
                        modifier = Modifier.width(150.dp).padding(end = 16.dp).clickable{},
                    )
                }
            }

            Spacer(modifier = Modifier.height(200.dp))

            val firstNameLabel = stringResource(MR.strings.form_text_field_label_first_name)

            Column(modifier = Modifier.semantics(true) {
                contentDescription = "Sekce - jména"
            }) {
                TextField(
                    value = viewState.firstName,
                    onValueChange = actions::onFirstNameChange,
                    label = {
                        Text(
                            text = firstNameLabel,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp).semantics {
                        contentDescription = viewState.firstName.takeIf { it.isNotBlank() } ?: firstNameLabel
                    },
                )

                val surnameLabel = stringResource(MR.strings.form_text_field_label_surname)
                TextField(
                    value = viewState.surname,
                    onValueChange = actions::onSurnameChange,
                    label = {
                        Text(
                            text = surnameLabel,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp).semantics {
                        contentDescription = surnameLabel
                    },
                )
            }


            val emailLabel = stringResource(MR.strings.form_text_field_label_email)
            TextField(
                value = viewState.email,
                onValueChange = actions::onEmailChange,
                label = {
                    Text(
                        text = emailLabel,
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp).semantics {
                    contentDescription = emailLabel
                },
            )

            val phoneLabel = stringResource(MR.strings.form_text_field_label_phone)
            TextField(
                value = viewState.phone,
                onValueChange = actions::onPhoneChange,
                label = {
                    Text(
                        text = phoneLabel,
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp).semantics {
                    this.contentDescription = phoneLabel
                },
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 40.dp)
                    .testTag(stringResource(MR.strings.form_text_field_label_password)),
            )

            Row {
                Button(onClick = { actions.onNext() }) {
                    Text(text = stringResource(MR.strings.form_button_interop_screen))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { actions.onShowAlertDialog() }) {
                    Text(text = stringResource(MR.strings.form_alert_test))
                }
            }


        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    MyApplicationTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Content(
                viewState = FormViewState(),
                actions = object : FormScreen.Actions{
                    override fun onBack() {
                        TODO("Not yet implemented")
                    }

                    override fun onFirstNameChange(string: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onSurnameChange(string: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onEmailChange(string: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onPhoneChange(string: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onPasswordChange(string: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onNext() {
                    }

                    override fun onShowAlertDialog() {
                        TODO("Not yet implemented")
                    }
                },
                snackbarState = SnackbarHostState(),
            )
        }

    }
}

