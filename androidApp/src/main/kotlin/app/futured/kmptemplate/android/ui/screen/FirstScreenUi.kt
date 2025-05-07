@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.android.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature.ui.firstScreen.FirstUiEvent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource
import app.futured.kmptemplate.resources.localized
import app.futured.kmptemplate.ui.MyApplicationTheme
import dev.icerock.moko.resources.desc.desc

@Composable
fun FirstScreenUi(
    screen: FirstScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Content(viewState = viewState, actions = actions, modifier = modifier)

    EventsEffect(eventsFlow = screen.events) {
        onEvent<FirstUiEvent.ShowToast> { event ->
            Toast.makeText(context, event.text.toString(context), Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun Content(
    viewState: FirstViewState,
    actions: FirstScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(kmpStringResource(res = MR.strings.first_screen_title)) },
                modifier = Modifier.fillMaxWidth(),
            )
        },
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
                Text(text = kmpStringResource(MR.strings.first_screen_button))
            }
            Button(onClick = { actions.switchToComposeMultiplatform() }) {
                Text(text = kmpStringResource(MR.strings.generic_switch_to_multiplatform))
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

@Preview
@Composable
private fun FirstScreenPreview() {
    val actions = object : FirstScreen.Actions {
        override fun onNext() = Unit
        override fun switchToComposeMultiplatform() = Unit
        override fun switchToNative() = Unit
    }
    MyApplicationTheme {
        Surface {
            Content(
                viewState = FirstViewState(text = "Hey there".desc()),
                actions = actions,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
