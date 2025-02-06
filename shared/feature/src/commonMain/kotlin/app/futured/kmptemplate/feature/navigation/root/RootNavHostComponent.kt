package app.futured.kmptemplate.feature.navigation.root

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.navigation.deepLink.DeepLinkDestination
import app.futured.kmptemplate.feature.navigation.deepLink.DeepLinkResolver
import app.futured.kmptemplate.feature.navigation.signedIn.SignedInNavHostComponent
import app.futured.kmptemplate.feature.navigation.signedIn.SignedInNavHostNavigation
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.AppComponentFactory
import app.futured.kmptemplate.feature.ui.loginScreen.LoginComponentFactory
import app.futured.kmptemplate.feature.ui.loginScreen.LoginScreenNavigation
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenArgs
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class RootNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    private val deepLinkResolver: DeepLinkResolver,
) : AppComponent<RootNavHostViewState, Nothing>(componentContext, RootNavHostViewState), RootNavHost {

    private val slotNavigator = SlotNavigation<RootConfig>()
    private var pendingDeepLink: DeepLinkDestination? = null

    private val logger = Logger.withTag("RootNavHostComponent")

    override val slot: StateFlow<ChildSlot<RootConfig, RootChild>> = childSlot(
        source = slotNavigator,
        serializer = RootConfig.serializer(),
        initialConfiguration = { null },
        handleBackButton = false,
        childFactory = { config, childCtx ->
            when (config) {
                RootConfig.Login -> RootChild.Login(
                    LoginComponentFactory.createComponent(
                        childCtx,
                        LoginScreenNavigation(toSignedIn = { slotNavigator.activate(RootConfig.SignedIn()) }),
                    ),
                )

                is RootConfig.SignedIn -> RootChild.SignedIn(
                    navHost = AppComponentFactory.createComponent<SignedInNavHostComponent>(
                        childContext = childCtx,
                        config.initialConfig,
                        SignedInNavHostNavigation(
                            toLogin = { slotNavigator.activate(RootConfig.Login) },
                        ),
                    ),
                )
            }
        },
    ).asStateFlow(coroutineScope = componentCoroutineScope)

    init {
        doOnCreate {
            if (!consumeDeepLink()) {
                slotNavigator.activate(RootConfig.Login)
            }
        }
    }

    override val actions: RootNavHost.Actions = object : RootNavHost.Actions {
        override fun onDeepLink(uri: String) {
            val deepLinkDestination = deepLinkResolver.resolve(uri) ?: return
            pendingDeepLink = deepLinkDestination
            consumeDeepLink()
        }
    }

    /**
     * Consumes pending deep links.
     *
     * @return `true` if deep link was consumed, or `false` if there was no deep link to consume.
     */
    private fun consumeDeepLink(): Boolean {
        val deepLink = pendingDeepLink ?: return false
        when (deepLink) {
            DeepLinkDestination.HomeTab -> slotNavigator.activate(RootConfig.deepLinkHome())
            DeepLinkDestination.ProfileTab -> slotNavigator.activate(RootConfig.deepLinkProfile())
            DeepLinkDestination.SecondScreen -> slotNavigator.activate(RootConfig.deepLinkSecondScreen())
            is DeepLinkDestination.ThirdScreen -> slotNavigator.activate(RootConfig.deepLinkThirdScreen(ThirdScreenArgs(deepLink.argument)))
        }
        return true
    }
}
