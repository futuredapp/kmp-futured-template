package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.android.ui.screen.LoginScreenUi
import app.futured.kmptemplate.feature.navigation.root.RootNavigation
import app.futured.kmptemplate.feature.navigation.root.RootNavigationEntry

@Composable
fun RootNavGraph(
    rootNavigation: RootNavigation,
    modifier: Modifier = Modifier,
) {
    val slot by rootNavigation.slot.collectAsState()

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        when (val childInstance = slot.child?.instance) {
            is RootNavigationEntry.Login -> LoginScreenUi(
                screen = childInstance.screen,
                modifier = Modifier.fillMaxSize(),
            )

            is RootNavigationEntry.Home -> HomeNavGraph(
                homeNavigation = childInstance.navigation,
                modifier = Modifier.fillMaxSize(),
            )

            else -> Unit
        }
    }
}
