package app.futured.kmptemplate.feature.navigation.signedin.tab.b

import app.futured.arkitekt.decompose.AppComponentContext
import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.componentCoroutineScope
import app.futured.kmptemplate.feature.navigation.root.RootNavigator
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Single

internal interface TabBNavigator {
    fun createStack(
        componentContext: AppComponentContext,
        initialStack: List<TabBDestination>,
    ): StateFlow<ChildStack<TabBDestination, TabBNavEntry>>

    fun pop()
    fun iosPop(newStack: List<Child<TabBDestination, TabBNavEntry>>)

    fun navigateToSecond()
    fun navigateToThird()
}

@Single
internal class TabBNavigatorImpl(
    private val rootNavigator: RootNavigator,
) : TabBNavigator {

    private val stackNavigator: StackNavigation<TabBDestination> = StackNavigation()

    override fun createStack(
        componentContext: AppComponentContext,
        initialStack: List<TabBDestination>,
    ): StateFlow<ChildStack<TabBDestination, TabBNavEntry>> = componentContext.childStack(
        source = stackNavigator,
        serializer = TabBDestination.serializer(),
        key = this::class.simpleName.toString(),
        initialStack = { initialStack },
        handleBackButton = true,
        childFactory = { config, childContext -> config.createComponent(childContext) },
    )
        .asStateFlow(componentContext.componentCoroutineScope())

    override fun pop() {
        stackNavigator.pop { success ->
            // Switch back to login slot if there were no more destinations to pop to
            if (!success) {
                rootNavigator.setLogin()
            }
        }
    }

    override fun iosPop(newStack: List<Child<TabBDestination, TabBNavEntry>>) =
        stackNavigator.navigate { newStack.map { it.configuration } }

    override fun navigateToSecond() = stackNavigator.push(TabBDestination.Second)

    override fun navigateToThird() = stackNavigator.push(TabBDestination.Third)
}
