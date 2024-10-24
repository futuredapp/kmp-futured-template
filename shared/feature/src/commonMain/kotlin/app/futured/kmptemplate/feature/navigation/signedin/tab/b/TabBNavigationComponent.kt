package app.futured.kmptemplate.feature.navigation.signedin.tab.b

import app.futured.kmptemplate.feature.AppComponentContext
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class TabBNavigationComponent(
    componentContext: AppComponentContext,
    initialStack: List<TabBDestination>,
) : TabBNavigation,
    TabBNavigation.Actions,
    AppComponentContext by componentContext,
    KoinComponent {

    private val navigator: TabBNavigator by inject()

    override val stack: StateFlow<ChildStack<TabBDestination, TabBNavEntry>> = navigator.createStack(
        componentContext = componentContext,
        initialStack = initialStack,
    )

    override val actions: TabBNavigation.Actions = this

    override fun iosPopTo(newStack: List<Child<TabBDestination, TabBNavEntry>>) = navigator.iosPop(newStack)
    override fun onBack() = navigator.pop()
}
