@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreen
import app.futured.kmptemplate.feature.ui.secondScreen.SecondViewState
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SecondScreenUi(
    screen: SecondScreen,
    modifier: Modifier = Modifier,
) {
    val viewState by screen.viewState.collectAsStateWithLifecycle()

    Content(viewState = viewState, actions = screen.actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: SecondViewState,
    actions: SecondScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(MR.strings.second_screen_title)) },
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets.navigationBars,
                navigationIcon = {
                    IconButton(onClick = { actions.onBack() }) {
                        Icon(
                            painter = painterResource(MR.images.ic_back),
                            contentDescription = null,
                        )
                    }
                },
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
            Text(text = viewState.createdAtText.localized())
            Row(Modifier.padding(horizontal = 20.dp)) {
                Button(onClick = { actions.onPickFruit() }, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(MR.strings.second_screen_button_fruit))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { actions.onPickVeggie() }, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(MR.strings.second_screen_button_veggie))
                }
            }
        }
    }
}
