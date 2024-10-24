package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.componentCoroutineScope
import app.futured.kmptemplate.feature.AppComponentContext
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
        componentContext: AppComponentContext,
    ): StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>>

    /**
     * Brings component with [dest] class to front of the stack, but **does not** recreate component if there's a component of the same class
     * and classes are not equal.
     */
    fun switchTab(dest: SignedInDestination, onComplete: () -> Unit = {})

    /**
     * Removes all components with configurations of [dest]'s class, and adds the provided [dest] to the top of the stack.
     */
    fun bringTabToFront(dest: SignedInDestination, onComplete: () -> Unit = {})

    fun pop()
}

@Single
internal class SignedInNavigatorImpl : SignedInNavigator {

    private val stackNavigator = StackNavigation<SignedInDestination>()

    override fun createStack(
        componentContext: AppComponentContext,
    ): StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>> = componentContext.childStack(
        source = stackNavigator,
        serializer = SignedInDestination.serializer(),
        initialStack = { SignedInNavigationDefaults.getInitialStack() },
        handleBackButton = true,
        childFactory = { dest, childContext ->
            dest.createComponent(childContext)
        },
    ).asStateFlow(componentContext.componentCoroutineScope())

    override fun switchTab(dest: SignedInDestination, onComplete: () -> Unit) {
        stackNavigator.switchTab(dest, onComplete)
    }

    override fun bringTabToFront(dest: SignedInDestination, onComplete: () -> Unit) {
        stackNavigator.bringToFront(dest, onComplete)
    }

    override fun pop() = stackNavigator.pop()

    /**
     * The same as [StackNavigation.bringToFront] but does not recreate [configuration] if it's class is already on stack and
     * the classes are not equal.
     */
    private inline fun <C : Any> StackNavigator<C>.switchTab(configuration: C, crossinline onComplete: () -> Unit = {}) {
        navigate(
            transformer = { stack ->
                val existing = stack.find { it::class == configuration::class }
                if (existing != null) {
                    stack.filterNot { it::class == configuration::class } + existing
                } else {
                    stack + configuration
                }
            },
            onComplete = { _, _ -> onComplete() },
        )
    }
}
