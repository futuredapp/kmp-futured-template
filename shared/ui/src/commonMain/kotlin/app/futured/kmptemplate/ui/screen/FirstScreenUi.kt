@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen.Actions.Companion.noOpActions
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreenPreviews
import app.futured.kmptemplate.feature.ui.firstScreen.FirstUiEvent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.ui.components.Showcase
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.format
import kotlinx.coroutines.launch

@Composable
fun FirstScreenUi(
    screen: FirstScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val message = MR.strings.first_screen_button.format(30L).localized()

    EventsEffect(eventsFlow = screen.events) {
        when (this) {
            is FirstUiEvent.ShowToast -> {
                scope.launch { snackbarHostState.showSnackbar(message) }
            }
        }
    }

    Content(viewState = viewState, actions = actions, snackbarHostState = snackbarHostState, modifier = modifier)
}

@Composable
private fun Content(
    viewState: FirstViewState,
    actions: FirstScreen.Actions,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(MR.strings.first_screen_title)) },
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets.navigationBars,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = viewState.counter.localized())
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = viewState.createdAt.localized())
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(viewState.randomPerson != null) {
                viewState.randomPerson?.let { person ->
                    Column {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = person.localized(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { actions.onNext() }) {
                Text(text = stringResource(MR.strings.first_screen_button))
            }
        }
    }
}

@Preview
@Composable
private fun FirstScreenPreview() = Showcase {
    Surface {
        Content(
            viewState = FirstScreenPreviews.viewState(),
            actions = noOpActions(),
            snackbarHostState = remember { SnackbarHostState() },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
