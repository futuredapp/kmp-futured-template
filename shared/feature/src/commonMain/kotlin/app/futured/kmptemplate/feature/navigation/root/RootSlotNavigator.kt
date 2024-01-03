package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot

class RootSlotNavigator(
    private val slotNavigator: SlotNavigation<RootDestination>,
) : SlotNavigation<RootDestination> by slotNavigator {

    internal fun createSlot(componentContext: ComponentContext) = componentContext.childSlot(
        source = slotNavigator,
        serializer = RootDestination.serializer(),
        initialConfiguration = { RootDestination.Login },
        handleBackButton = false,
        childFactory = { destination, childContext ->
            destination.createComponent(childContext)
        },
    ).asStateFlow(componentContext.componentCoroutineScope())
}
