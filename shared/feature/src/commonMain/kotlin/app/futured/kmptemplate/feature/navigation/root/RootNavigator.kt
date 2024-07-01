package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkDestination
import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkNavigator
import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Single

internal interface RootNavigator {
    fun createSlot(componentContext: ComponentContext): StateFlow<ChildSlot<RootDestination, RootEntry>>
    fun setLogin()
    fun setSignedIn()
}

@Single
internal class RootNavigatorImpl : RootNavigator, DeepLinkNavigator {

    private val slotNavigator: SlotNavigation<RootDestination> = SlotNavigation()

    override fun createSlot(componentContext: ComponentContext) = componentContext.childSlot(
        source = slotNavigator,
        serializer = RootDestination.serializer(),
        initialConfiguration = { RootDestination.Login },
        handleBackButton = false,
        childFactory = { destination, childContext ->
            destination.createComponent(childContext)
        },
    ).asStateFlow(componentContext.componentCoroutineScope())

    override fun setLogin() = slotNavigator.activate(RootDestination.Login)

    override fun setSignedIn() = slotNavigator.activate(RootDestination.SignedIn())

    override fun openDeepLink(destination: DeepLinkDestination) {
        // TODO deep links
//        slotNavigator.activate(RootDestination.Home(args = HomeNavigationArgs(deepLinkDestination = destination)))
    }
}
