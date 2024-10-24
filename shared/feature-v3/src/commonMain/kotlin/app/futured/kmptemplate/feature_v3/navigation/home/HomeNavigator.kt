package app.futured.kmptemplate.feature_v3.navigation.home

import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstScreenNavigationActions
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew

internal interface HomeNavigator : HomeNavHost.Actions, FirstScreenNavigationActions

internal class HomeNavigatorImpl(
    private val stackNavigation: StackNavigation<HomeConfig> = StackNavigation(),
) : StackNavigation<HomeConfig> by stackNavigation, HomeNavigator {

    override fun navigate(newStack: List<Child<HomeConfig, HomeChild>>) = stackNavigation.navigate { newStack.map { it.configuration } }
    override fun pop() = stackNavigation.pop()
    override fun navigateToSecond() = stackNavigation.pushNew(HomeConfig.Second)
}
