package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.feature.navigation.signedin.tab.SignedInSlotDestination
import app.futured.kmptemplate.feature.navigation.signedin.tab.SignedInSlotNavEntry
import app.futured.kmptemplate.feature.ui.first.KoinAppComponentFactory
import app.futured.kmptemplate.feature.ui.picker.PickerComponent
import app.futured.kmptemplate.feature.ui.picker.PickerNavigationActions
import app.futured.kmptemplate.feature.ui.picker.PickerResult
import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
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

    fun createSlot(
        componentContext: AppComponentContext,
    ): StateFlow<ChildSlot<SignedInSlotDestination, SignedInSlotNavEntry>>

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

    fun showPicker()

    fun dismissSlot()
}

@Single
internal class SignedInNavigatorImpl : SignedInNavigator {

    private val stackNavigator = StackNavigation<SignedInDestination>()
    private val slotNavigator = SlotNavigation<SignedInSlotDestination>()

    override fun createStack(
        componentContext: AppComponentContext,
    ): StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>> = componentContext.childStack(
        source = stackNavigator,
        serializer = SignedInDestination.serializer(),
        initialStack = { SignedInNavigationDefaults.getInitialStack() },
        handleBackButton = false,
        childFactory = { dest, childContext ->
            dest.createComponent(childContext)
        },
        key = "Stack",
    ).asStateFlow(componentContext.componentCoroutineScope())

    override fun createSlot(
        componentContext: AppComponentContext,
    ): StateFlow<ChildSlot<SignedInSlotDestination, SignedInSlotNavEntry>> = componentContext.childSlot(
        source = slotNavigator,
        serializer = SignedInSlotDestination.serializer(),
        initialConfiguration = { null },
        handleBackButton = false,
        childFactory = { dest, childContext ->
            val factory = KoinAppComponentFactory(childContext)
            when (dest) {
                SignedInSlotDestination.Picker -> SignedInSlotNavEntry.Picker(
                    factory.createComponent<PickerComponent>(
                        object : PickerNavigationActions {
                            override fun onResult(result: PickerResult) {
                                // TODO https://arkivanov.github.io/Decompose/navigation/stack/overview/#delivering-a-result-when-navigating-back
                            }
                        },
                    ),
                )
            }
        },
        key = "Slot",
    ).asStateFlow(componentContext.componentCoroutineScope())

    override fun switchTab(dest: SignedInDestination, onComplete: () -> Unit) {
        stackNavigator.switchTab(dest, onComplete)
    }

    override fun bringTabToFront(dest: SignedInDestination, onComplete: () -> Unit) {
        stackNavigator.bringToFront(dest, onComplete)
    }

    override fun pop() = stackNavigator.pop()

    override fun showPicker() {
        slotNavigator.activate(SignedInSlotDestination.Picker)
    }

    override fun dismissSlot() = slotNavigator.dismiss()

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
