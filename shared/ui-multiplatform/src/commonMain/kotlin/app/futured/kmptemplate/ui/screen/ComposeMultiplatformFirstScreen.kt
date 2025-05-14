@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature.ui.firstScreen.FirstUiEvent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.ui.tools.SnackbarHost
import app.futured.kmptemplate.ui.tools.SnackbarHostState
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.stringResource


@Composable
fun ComposeMultiplatformFirstScreen(
    screen: FirstScreen,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
    val snackbarState = remember { SnackbarHostState() }

    Content(viewState = viewState, actions = actions, modifier = modifier, snackbarState)

    EventsEffect(eventsFlow = screen.events) {
        onEvent<FirstUiEvent.ShowToast> { event ->
            snackbarState.showSnackbar(event.text)
        }
    }

}


@Composable
private fun Content(
    viewState: FirstViewState,
    actions: FirstScreen.Actions,
    modifier: Modifier = Modifier,
    snackbarState: SnackbarHostState,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(MR.strings.first_screen_multiplatform_title)) },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        snackbarHost = { SnackbarHost(snackbarState) },
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = viewState.text.localized())
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = { actions.onNext() }) {
                Text(text = stringResource(MR.strings.first_screen_button))
            }
            Button(onClick = { actions.switchToNative() }) {
                Text(text = stringResource(MR.strings.generic_switch_to_native))
            }
            viewState.randomPerson?.let { person ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = person.localized(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
