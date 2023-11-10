package app.futured.kmpfuturedtemplate.android.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.first.FirstViewState
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun FirstScreenUi(
    screen: FirstScreen,
    modifier: Modifier = Modifier
) {
    val actions = screen.actions
    val viewState by screen.viewState.subscribeAsState()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: FirstViewState,
    actions: FirstScreen.Actions,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = "Hi from First + ${viewState.title}")
        Button(onClick = actions::onNext) {
            Text(text = "To Second")
        }
    }
}
