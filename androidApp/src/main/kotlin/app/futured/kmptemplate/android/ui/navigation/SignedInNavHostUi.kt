package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.feature.navigation.signedIn.NavigationTab
import app.futured.kmptemplate.feature.navigation.signedIn.SignedInChild
import app.futured.kmptemplate.feature.navigation.signedIn.SignedInConfig
import app.futured.kmptemplate.feature.navigation.signedIn.SignedInNavHost
import app.futured.kmptemplate.feature.navigation.signedIn.SignedInNavHostViewState
import app.futured.kmptemplate.resources.localized
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandler
import timber.log.Timber

@Composable
fun SignedInNavHostUi(
    navHost: SignedInNavHost,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<SignedInConfig, SignedInChild> by navHost.stack.collectAsStateWithLifecycle()
    val viewState: SignedInNavHostViewState by navHost.viewState.collectAsStateWithLifecycle()
    val actions = navHost.actions

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AnimatedVisibility(visible = WindowInsets.ime.getBottom(LocalDensity.current) ==0 ) {
                NavigationBar(modifier = Modifier
                    .fillMaxWidth()) {
                    for (tab in viewState.navigationTabs) {
                        NavigationBarItem(
                            selected = tab == viewState.selectedTab,
                            onClick = { actions.onTabSelected(tab) },
                            icon = { Icon(tab.icon, contentDescription = null) },
                            label = { Text(text = tab.title.localized()) },
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        TabsContent(
            stack = stack,
            backHandler = navHost.backHandler,
            onBack = actions::onBack,
            modifier = Modifier
                .padding(paddingValues)
                .imePadding()
        )
    }
}

private val NavigationTab.icon: ImageVector
    get() = when (this) {
        NavigationTab.HOME -> Icons.Filled.Home
        NavigationTab.PROFILE -> Icons.Filled.AccountCircle
        NavigationTab.CMP -> Icons.Filled.Build
    }

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun TabsContent(
    stack: ChildStack<SignedInConfig, SignedInChild>,
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
            is SignedInChild.Home -> HomeNavHostUi(
                navHost = childInstance.navHost,
                modifier = Modifier.fillMaxSize(),
            )

            is SignedInChild.Profile -> ProfileNavHostUi(
                navHost = childInstance.navHost,
                modifier = Modifier.fillMaxSize(),
            )

            is SignedInChild.Cmp -> CmpNavHostUi(
                navHost = childInstance.navHost,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
