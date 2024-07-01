package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.StackNavigator
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Single

internal interface SignedInNavigator {

    fun createStack(
        componentContext: ComponentContext,
        initialStack: List<SignedInDestination>,
    ): StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>>

    fun selectTab(tab: SignedInNavigationViewState.Tab)
    fun pop()
}

@OptIn(ExperimentalDecomposeApi::class)
@Single
internal class SignedInNavigatorImpl : SignedInNavigator {

    private val stackNavigator = StackNavigation<SignedInDestination>()

    override fun createStack(
        componentContext: ComponentContext,
        initialStack: List<SignedInDestination>,
    ): StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>> = componentContext.childStack(
        source = stackNavigator,
        serializer = SignedInDestination.serializer(),
        initialStack = {
            initialStack
        },
        handleBackButton = true,
        childFactory = { dest, childContext ->
            dest.createComponent(childContext)
        },
    ).asStateFlow(componentContext.componentCoroutineScope())

    override fun selectTab(tab: SignedInNavigationViewState.Tab) {
        when (tab) {
            SignedInNavigationViewState.Tab.A -> stackNavigator.switchTab(SignedInDestination.A)
            SignedInNavigationViewState.Tab.B -> stackNavigator.switchTab(SignedInDestination.B())
            SignedInNavigationViewState.Tab.C -> stackNavigator.switchTab(SignedInDestination.C)
        }
    }

    override fun pop() = stackNavigator.pop()

    /**
     * The same as [StackNavigation.bringToFront] but does not recreate [configuration] if it's class is already on stack and
     * the classes do not equal.
     */
    private inline fun <C : Any> StackNavigator<C>.switchTab(configuration: C, crossinline onComplete: () -> Unit = {}) {
        navigate(
            transformer = { stack ->
                val existing = stack.find { it::class == configuration::class }
                if (existing != null) {
                    stack.filterNot { it::class == configuration::class } + existing
                } else {
                    stack.filterNot { it::class == configuration::class } + configuration
                }
            },
            onComplete = { _, _ -> onComplete() },
        )
    }
}
