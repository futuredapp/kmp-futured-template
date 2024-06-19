package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import app.futured.kmptemplate.android.ui.screen.LoginScreenUi
import app.futured.kmptemplate.feature.navigation.root.RootDestination
import app.futured.kmptemplate.feature.navigation.root.RootEntry
import app.futured.kmptemplate.feature.navigation.root.RootNavigation
import com.arkivanov.decompose.router.slot.ChildSlot

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RootNavGraph(
    rootNavigation: RootNavigation,
    modifier: Modifier = Modifier,
) {
    val slot: ChildSlot<RootDestination, RootEntry> by rootNavigation.slot.collectAsState()

    Surface(
        modifier.semantics {
            testTagsAsResourceId = true
        },
        color = MaterialTheme.colorScheme.background,
    ) {
        when (val childInstance = slot.child?.instance) {
            is RootEntry.Home -> HomeNavGraph(
                homeNavigation = childInstance.navigation,
                modifier = Modifier.fillMaxSize(),
            )

            is RootEntry.Login -> LoginScreenUi(
                screen = childInstance.screen,
                modifier = Modifier.fillMaxSize(),
            )

            else -> Unit
        }
    }
}
