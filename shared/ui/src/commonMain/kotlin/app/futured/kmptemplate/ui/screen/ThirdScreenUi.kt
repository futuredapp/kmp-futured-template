@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdViewState
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ThirdScreenUi(
    screen: ThirdScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: ThirdViewState,
    actions: ThirdScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(MR.strings.third_screen_title)) },
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
            Text(text = viewState.text.localized())
        }
    }
}
