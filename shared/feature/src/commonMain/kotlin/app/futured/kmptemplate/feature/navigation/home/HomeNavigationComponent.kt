package app.futured.kmptemplate.feature.navigation.home

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class HomeNavigationComponent(
    componentContext: ComponentContext,
    args: HomeNavigationArgs,
) : HomeNavigation, HomeNavigation.Actions, ComponentContext by componentContext, KoinComponent, BackHandlerOwner {

    private val homeNavigator: HomeStackNavigator by inject()

    override val stack: StateFlow<ChildStack<HomeDestination, HomeEntry>> =
        homeNavigator.createStack(componentContext, args.deepLinkDestination)

    override val actions: HomeNavigation.Actions = this

    override val backHandler = componentContext.backHandler

    override fun iosPopTo(newStack: List<Child<HomeDestination, HomeEntry>>) = homeNavigator.iosPop(newStack)

    override fun onBack() = homeNavigator.pop()
}
