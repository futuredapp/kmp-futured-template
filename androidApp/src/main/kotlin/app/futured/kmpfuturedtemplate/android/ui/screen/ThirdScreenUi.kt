package app.futured.kmpfuturedtemplate.android.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.feature.ui.third.ThirdViewState
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun ThirdScreenUi(
    screen: ThirdScreen,
    modifier: Modifier = Modifier
) {
    val actions = screen.actions
    val viewState by screen.viewState.subscribeAsState()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: ThirdViewState,
    actions: ThirdScreen.Actions,
    modifier: Modifier = Modifier
) {
    Text(text = "Hi from Third")
}
