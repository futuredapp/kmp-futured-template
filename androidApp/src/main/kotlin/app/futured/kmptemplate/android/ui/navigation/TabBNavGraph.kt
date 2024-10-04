@file:OptIn(ExperimentalDecomposeApi::class)

package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.android.ui.screen.FirstScreenUi
import app.futured.kmptemplate.android.ui.screen.SecondScreenUi
import app.futured.kmptemplate.android.ui.screen.SecretScreenUi
import app.futured.kmptemplate.android.ui.screen.ThirdScreenUi
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBDestination
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavEntry
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigation
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack

@Composable
fun TabBNavGraph(
    navigation: TabBNavigation,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<TabBDestination, TabBNavEntry> by navigation.stack.collectAsState()
    val actions = navigation.actions

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        Children(
            stack = stack,
            modifier = Modifier.fillMaxSize(),
            animation = predictiveBackAnimation(
                backHandler = navigation.backHandler,
                onBack = actions::onBack,
                selector = { backEvent, _, _ -> androidPredictiveBackAnimatable(backEvent) },
            ),
        ) { child ->
            when (val childInstance = child.instance) {
                is TabBNavEntry.First -> FirstScreenUi(screen = childInstance.instance, modifier = Modifier.fillMaxSize())
                is TabBNavEntry.Second -> SecondScreenUi(screen = childInstance.instance, modifier = Modifier.fillMaxSize())
                is TabBNavEntry.Third -> ThirdScreenUi(screen = childInstance.instance, modifier = Modifier.fillMaxSize())
                is TabBNavEntry.Secret -> SecretScreenUi(screen = childInstance.instance, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
