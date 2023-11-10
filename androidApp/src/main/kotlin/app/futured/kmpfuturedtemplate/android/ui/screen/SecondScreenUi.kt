package app.futured.kmpfuturedtemplate.android.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.feature.ui.second.SecondViewState
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun SecondScreenUi(
    screen: SecondScreen,
    modifier: Modifier = Modifier
) {
    val actions = screen.actions
    val viewState by screen.viewState.subscribeAsState()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: SecondViewState,
    actions: SecondScreen.Actions,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = "Hi from Second")
        Button(onClick = actions::onNext) {
            Text(text = "To Third")
        }
    }
}
