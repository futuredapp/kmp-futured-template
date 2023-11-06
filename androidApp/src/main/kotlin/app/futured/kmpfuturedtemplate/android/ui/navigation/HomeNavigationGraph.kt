package app.futured.kmpfuturedtemplate.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.futured.kmpfuturedtemplate.android.ui.screen.FirstScreenUi
import app.futured.kmpfuturedtemplate.android.ui.screen.SecondScreenUi
import app.futured.kmpfuturedtemplate.android.ui.screen.ThirdScreenUi
import app.futured.kmptemplate.feature.navigation.home.HomeNavigation
import app.futured.kmptemplate.feature.navigation.home.HomeNavigationEntry
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun HomeNavGraph(
    homeNavigation: HomeNavigation,
    modifier: Modifier = Modifier,
) {
    val stack by homeNavigation.stack.collectAsState()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        Children(modifier = Modifier.fillMaxSize(), stack = stack) { child ->
            when (val childInstance = child.instance) {
                is HomeNavigationEntry.First -> FirstScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                is HomeNavigationEntry.Second -> SecondScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                is HomeNavigationEntry.Third -> ThirdScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
