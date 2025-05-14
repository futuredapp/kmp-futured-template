@file:OptIn(ExperimentalLayoutApi::class)

package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.recalculateWindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.navigation.cmp.CmpChild
import app.futured.kmptemplate.feature.navigation.cmp.CmpConfig
import app.futured.kmptemplate.feature.navigation.cmp.CmpNavHost
import app.futured.kmptemplate.ui.screen.ComposeMultiplatformFormScreen
import app.futured.kmptemplate.ui.screen.ComposeMultiplatformInteropCheckScreen
import app.futured.kmptemplate.ui.screen.NativeComponentInteropCheckScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun CmpNavHostUi(
    navHost: CmpNavHost,
    modifier: Modifier = Modifier,
) {

    val stack: ChildStack<CmpConfig, CmpChild> by navHost.stack.collectAsStateWithLifecycle()
    val actions = navHost.actions


    Children(
        stack = stack,
        modifier = modifier,
        animation = predictiveBackAnimation(
            backHandler = navHost.backHandler,
            onBack = actions::pop,
            selector = { backEvent, _, _ -> androidPredictiveBackAnimatable(backEvent) },
        ),
    ) { child ->
        when (val childInstance = child.instance) {
            is CmpChild.Form -> ComposeMultiplatformFormScreen(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
            is CmpChild.InteropCheck -> ComposeMultiplatformInteropCheckScreen(screen = childInstance.screen, nativeComponent = { screen, nativeModifier ->
                NativeComponentInteropCheckScreen(screen, nativeModifier)
            }, modifier = Modifier.fillMaxSize())
        }
    }

}
