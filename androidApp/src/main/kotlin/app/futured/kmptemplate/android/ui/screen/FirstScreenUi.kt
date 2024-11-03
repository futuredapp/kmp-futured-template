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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.android.MyApplicationTheme
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstUiEvent
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource
import app.futured.kmptemplate.resources.localized
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
            Toast.makeText(context, event.text, Toast.LENGTH_SHORT).show()
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
            Text(text = viewState.text.localized())
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = { actions.onNext() }) {
                Text(text = "Go to second screen")
            }
        }
    }
}

@Preview
@Composable
private fun FirstScreenPreview() {
    val actions = object : FirstScreen.Actions {
        override fun onNext() = Unit
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
