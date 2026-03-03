package app.futured.kmptemplate.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.ui._template.TEMPLATEScreen
import app.futured.kmptemplate.feature.ui._template.TEMPLATEScreen.Actions.Companion.noOpActions
import app.futured.kmptemplate.feature.ui._template.TEMPLATEScreenPreviews
import app.futured.kmptemplate.feature.ui._template.TEMPLATEViewState
import app.futured.kmptemplate.ui.components.Showcase

/**
 * This is a template for creating new Compose screens:
 *
 * 1. Copy wherever you wanna create the UI for the screen.
 * 2. Select all occurrences of "TEMPLATE" (Ctrl + G) and rename to your liking.
 */
@Composable
fun TEMPLATEScreenUi(
    screen: TEMPLATEScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle()

    Content(viewState = viewState, actions = actions, modifier = modifier)
}

@Composable
private fun Content(
    viewState: TEMPLATEViewState,
    actions: TEMPLATEScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Text(text = "TEMPLATEScreen", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
@Preview
private fun TEMPLATEScreenPreview() = Showcase {
    Content(
        viewState = TEMPLATEScreenPreviews.viewState(),
        actions = noOpActions(),
    )
}
