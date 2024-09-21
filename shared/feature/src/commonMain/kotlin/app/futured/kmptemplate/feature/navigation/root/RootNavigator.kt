package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Single

internal interface RootNavigator {
    fun createSlot(componentContext: AppComponentContext): StateFlow<ChildSlot<RootDestination, RootEntry>>
    fun setLogin(onComplete: () -> Unit = {})
    fun setSignedIn(onComplete: () -> Unit = {})
}

@Single
internal class RootNavigatorImpl : RootNavigator {

    private val slotNavigator: SlotNavigation<RootDestination> = SlotNavigation()

    override fun createSlot(componentContext: AppComponentContext) = componentContext.childSlot(
        source = slotNavigator,
        serializer = RootDestination.serializer(),
        initialConfiguration = { RootDestination.Login },
        handleBackButton = false,
        childFactory = { destination, childContext ->
            destination.createComponent(childContext)
        },
    ).asStateFlow(componentContext.componentCoroutineScope())

    override fun setLogin(onComplete: () -> Unit) = slotNavigator.activate(RootDestination.Login, onComplete)

    override fun setSignedIn(onComplete: () -> Unit) = slotNavigator.activate(RootDestination.SignedIn, onComplete)
}
