@file:OptIn(ExperimentalDecomposeApi::class)

package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.android.tools.Constants
import app.futured.kmptemplate.android.ui.screen.FirstScreenUi
import app.futured.kmptemplate.android.ui.screen.SecondScreenUi
import app.futured.kmptemplate.android.ui.screen.SecretScreenUi
import app.futured.kmptemplate.android.ui.screen.ThirdScreenUi
import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeEntry
import app.futured.kmptemplate.feature.navigation.home.HomeNavigation
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack

@Composable
fun HomeNavGraph(
    homeNavigation: HomeNavigation,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<HomeDestination, HomeEntry> by homeNavigation.stack.collectAsState()
    val actions = homeNavigation.actions

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        Children(
            stack = stack,
            modifier = Modifier.fillMaxSize(),
            /**
             * Predictive back animation support predictive gesture back navigation.
             * Is it necessary implement BackHandlerOwner in Components where you want to use predictive gesture animation.
             */
            animation = predictiveBackAnimation(
                backHandler = homeNavigation.backHandler,
                animation = stackAnimation(Constants.Navigation.STACK_ANIMATION_CROSS_FADE_SPEC),
                onBack = actions::onBack,
            ),
        ) { child ->
            when (val childInstance = child.instance) {
                is HomeEntry.First -> FirstScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                is HomeEntry.Second -> SecondScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                is HomeEntry.Third -> ThirdScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                is HomeEntry.Secret -> SecretScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
