package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.futured.kmptemplate.feature.navigation.signedin.SignedInDestination
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavEntry
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigation
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigationViewState
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
fun SignedInNavGraph(
    navigation: SignedInNavigation,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<SignedInDestination, SignedInNavEntry> by navigation.stack.collectAsState()
    val viewState: SignedInNavigationViewState by navigation.viewState.collectAsState()
    val actions = navigation.actions

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                NavigationBarItem(
                    selected = viewState.selectedTab == SignedInNavigationViewState.Tab.A,
                    onClick = { actions.onTabSelected(SignedInNavigationViewState.Tab.A) },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Tab A") },
                    label = { Text(text = "Tab A") },
                )

                NavigationBarItem(
                    selected = viewState.selectedTab == SignedInNavigationViewState.Tab.B,
                    onClick = { actions.onTabSelected(SignedInNavigationViewState.Tab.B) },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Tab B") },
                    label = { Text(text = "Tab B") },
                )

                NavigationBarItem(
                    selected = viewState.selectedTab == SignedInNavigationViewState.Tab.C,
                    onClick = { actions.onTabSelected(SignedInNavigationViewState.Tab.C) },
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Tab C") },
                    label = { Text(text = "Tab C") },
                )
            }
        },
    ) { paddingValues ->
        TabsContent(
            stack = stack,
            backHandler = navigation.backHandler,
            onBack = navigation.actions::onBack,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun TabsContent(
    stack: ChildStack<SignedInDestination, SignedInNavEntry>,
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
            is SignedInNavEntry.A -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Tab A")
                }
            }

            is SignedInNavEntry.B -> TabBNavGraph(navigation = childInstance.instance, modifier = Modifier.fillMaxSize())

            is SignedInNavEntry.C -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Tab C")
                }
            }

            null -> Unit
        }
    }
}