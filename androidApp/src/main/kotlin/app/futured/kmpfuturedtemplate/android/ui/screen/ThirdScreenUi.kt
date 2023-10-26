@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmpfuturedtemplate.android.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import app.futured.kmptemplate.feature.ui.third.ThirdViewState
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun ThirdScreenUi(
    screen: ThirdScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.subscribeAsState()

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
                title = { Text("Third screen") },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = { actions.onBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = viewState.text)
        }
    }
}
