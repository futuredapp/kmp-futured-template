package app.futured.kmptemplate.feature.navigation.signedin.tab.b

import app.futured.kmptemplate.feature.navigation.root.RootNavigator
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigator
import app.futured.kmptemplate.feature.ui.first.FirstComponent
import app.futured.kmptemplate.feature.ui.first.FirstNavigationActions
import app.futured.kmptemplate.feature.ui.first.KoinAppComponentFactory
import app.futured.kmptemplate.feature.ui.picker.PickerResult
import app.futured.kmptemplate.feature.ui.second.SecondComponent
import app.futured.kmptemplate.feature.ui.secret.SecretComponent
import app.futured.kmptemplate.feature.ui.third.ThirdComponent
import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
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
    private val signedInNavigator: SignedInNavigator,
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
        handleBackButton = false,
        childFactory = { config, childContext ->
            val factory = KoinAppComponentFactory(childContext)
            when (config) {
                TabBDestination.First -> TabBNavEntry.First(
                    factory.createComponent<FirstComponent>(
                        object : FirstNavigationActions {
                            override fun pop() = this@TabBNavigatorImpl.pop()
                            override fun navigateToSecond() = this@TabBNavigatorImpl.navigateToSecond()
                            override fun showPicker(onResult: (PickerResult) -> Unit) = signedInNavigator.showPicker(onResult)
                        },
                    ),
                )

                TabBDestination.Second -> TabBNavEntry.Second(SecondComponent(childContext))
                is TabBDestination.Secret -> TabBNavEntry.Secret(SecretComponent(config.argument))
                TabBDestination.Third -> TabBNavEntry.Third(ThirdComponent(componentContext))
            }
        },
    ).asStateFlow(componentContext.componentCoroutineScope())

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
