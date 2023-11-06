package app.futured.kmpfuturedtemplate.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.futured.kmpfuturedtemplate.android.ui.screen.FirstScreenUi
import app.futured.kmpfuturedtemplate.android.ui.screen.SecondScreenUi
import app.futured.kmpfuturedtemplate.android.ui.screen.ThirdScreenUi
import app.futured.kmptemplate.feature.navigation.home.HomeNavigation
import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children

@Composable
fun HomeNavGraph(
    homeNavigation: HomeNavigation,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = homeNavigation.stack,
        modifier = modifier,
    ) { child ->
        when (val childInstance = child.instance) {
            is FirstScreen -> FirstScreenUi(
                screen = childInstance,
                modifier = Modifier.fillMaxSize(),
            )

            is SecondScreen -> SecondScreenUi(
                screen = childInstance,
                modifier = Modifier.fillMaxSize(),
            )

            is ThirdScreen -> ThirdScreenUi(
                screen = childInstance,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
