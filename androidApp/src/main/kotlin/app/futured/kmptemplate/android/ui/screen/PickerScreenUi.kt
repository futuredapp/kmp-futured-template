package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import app.futured.kmptemplate.android.MyApplicationTheme
import app.futured.kmptemplate.feature.ui.picker.PickerScreen
import app.futured.kmptemplate.feature.ui.picker.PickerViewState

@Composable
fun PickerScreenUi(
    screen: PickerScreen,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewState by screen.viewState.collectAsState()
    val actions = screen.actions

    Content(
        viewState = viewState,
        actions = actions,
        onDismiss = dismiss,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    viewState: PickerViewState,
    actions: PickerScreen.Actions,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        CenterAlignedTopAppBar(
            windowInsets = WindowInsets(0),
            title = { Text("Pick a result") },
            actions = {
                IconButton(
                    onClick = {
                        actions.onDismissResult()
                        onDismiss()
                    },
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Dismiss")
                }
            },
        )
        for (item in viewState.items) {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        actions.onItemResult(item)
                        onDismiss()
                    },
                headlineContent = {
                    Text(item.id)
                },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PickerScreenUiPreview() {
    val actions = object : PickerScreen.Actions {
        override fun onItemResult(item: PickerViewState.Item) = Unit
        override fun onDismissResult() = Unit
    }

    MyApplicationTheme {
        Surface {
            Content(
                viewState = PickerViewState(),
                actions = actions,
                onDismiss = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
