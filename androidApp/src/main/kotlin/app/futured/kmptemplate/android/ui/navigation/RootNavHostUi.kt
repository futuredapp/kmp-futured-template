package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature_v3.navigation.root.NavigationTab
import app.futured.kmptemplate.feature_v3.navigation.root.RootChild
import app.futured.kmptemplate.feature_v3.navigation.root.RootConfig
import app.futured.kmptemplate.feature_v3.navigation.root.RootNavHost
import app.futured.kmptemplate.feature_v3.navigation.root.RootNavHostState
import app.futured.kmptemplate.resources.localized
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
fun RootNavHostUi(
    navHost: RootNavHost,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<RootConfig, RootChild> by navHost.stack.collectAsStateWithLifecycle()
    val viewState: RootNavHostState by navHost.viewState.collectAsStateWithLifecycle()
    val actions = navHost.actions

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                for (tab in viewState.navigationTabs) {
                    NavigationBarItem(
                        selected = tab == viewState.selectedTab,
                        onClick = { actions.onTabSelected(tab) },
                        icon = { Icon(tab.icon, contentDescription = null) },
                        label = { Text(text = tab.title.localized()) },
                    )
                }
            }
        },
    ) { paddingValues ->
        TabsContent(
            stack = stack,
            backHandler = navHost.backHandler,
            onBack = actions::onBack,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

private val NavigationTab.icon: ImageVector
    get() = when (this) {
        NavigationTab.HOME -> Icons.Filled.Home
        NavigationTab.PROFILE -> Icons.Filled.AccountCircle
    }

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun TabsContent(
    stack: ChildStack<RootConfig, RootChild>,
    backHandler: BackHandler,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = stack,
        modifier = modifier,
        animation = predictiveBackAnimation(
            backHandler = backHandler,
            onBack = onBack,
            selector = { backEvent, _, _ -> androidPredictiveBackAnimatable(backEvent) },
        ),
    ) { child ->
        when (val childInstance = child.instance) {
            is RootChild.Home -> HomeNavHostUi(
                navHost = childInstance.navHost,
                modifier = Modifier.fillMaxSize(),
            )

            is RootChild.Profile -> ProfileNavHostUi(
                navHost = childInstance.navHost,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}