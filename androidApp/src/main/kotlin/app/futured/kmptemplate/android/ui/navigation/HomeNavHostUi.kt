package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.android.ui.screen.FirstScreenUi
import app.futured.kmptemplate.android.ui.screen.SecondScreenUi
import app.futured.kmptemplate.android.ui.screen.ThirdScreenUi
import app.futured.kmptemplate.feature.navigation.home.HomeChild
import app.futured.kmptemplate.feature.navigation.home.HomeConfig
import app.futured.kmptemplate.feature.navigation.home.HomeNavHost
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun HomeNavHostUi(
    navHost: HomeNavHost,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<HomeConfig, HomeChild> by navHost.stack.collectAsStateWithLifecycle()
    val actions = navHost.actions

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.systemBars,
        content = { paddings ->
            Children(
                stack = stack,
                modifier = Modifier.padding(paddings),
                animation = predictiveBackAnimation(
                    backHandler = navHost.backHandler,
                    onBack = actions::pop,
                    selector = { backEvent, _, _ -> androidPredictiveBackAnimatable(backEvent) },
                ),
            ) { child ->
                when (val childInstance = child.instance) {
                    is HomeChild.First -> FirstScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                    is HomeChild.Second -> SecondScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                    is HomeChild.Third -> ThirdScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                }
            }
        },
    )
}
