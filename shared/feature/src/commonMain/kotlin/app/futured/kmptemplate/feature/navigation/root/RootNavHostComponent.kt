package app.futured.kmptemplate.feature.navigation.root

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.navigation.deepLink.DeepLinkDestination
import app.futured.kmptemplate.feature.navigation.deepLink.DeepLinkResolver
import app.futured.kmptemplate.feature.navigation.home.HomeNavHostComponentFactory
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.welcomeScreen.WelcomeComponentFactory
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class RootNavHostComponent(@InjectedParam componentContext: AppComponentContext, private val deepLinkResolver: DeepLinkResolver) :
    AppComponent<RootNavHostViewState, Nothing>(componentContext, RootNavHostViewState),
    RootNavHost {

    private val rootNavigator: RootNavHostNavigation = RootNavHostNavigator()
    private var pendingDeepLink: DeepLinkDestination? = null

    private val logger = Logger.withTag("RootNavHostComponent")

    override val slot: StateFlow<ChildSlot<RootConfig, RootChild>> = childSlot(
        source = rootNavigator.slotNavigator,
        serializer = RootConfig.serializer(),
        initialConfiguration = { null },
        handleBackButton = false,
        childFactory = { config, childCtx ->
            when (config) {
                RootConfig.Intro -> RootChild.Intro(
                    WelcomeComponentFactory.createComponent(
                        childCtx,
                        rootNavigator,
                    ),
                )

                is RootConfig.Home -> RootChild.Home(
                    navHost = HomeNavHostComponentFactory.createComponent(
                        componentContext = childCtx,
                        initialStack = config.initialStack,
                    ),
                )
            }
        },
    ).asStateFlow()


    override val actions: RootNavHost.Actions = object : RootNavHost.Actions {
        override fun onDeepLink(uri: String) {
            val deepLinkDestination = deepLinkResolver.resolve(uri) ?: return
            pendingDeepLink = deepLinkDestination
            consumeDeepLink()
        }

        override fun updateCameraPermission(allowed: Boolean) {
            if (!consumeDeepLink()) {
                rootNavigator.slotNavigator.activate(
                    if (allowed) {
                        RootConfig.Home()
                    } else {
                        RootConfig.Intro
                    },
                )
            }

        }
    }

    /**
     * Consumes pending deep links.
     *
     * @return `true` if deep link was consumed, or `false` if there was no deep link to consume.
     */
    private fun consumeDeepLink(): Boolean {
        val deepLink = pendingDeepLink ?: return false
        val deepLinkConfig = when (deepLink) {
            DeepLinkDestination.HomeTab -> RootConfig.deepLinkHome()
        }
        rootNavigator.slotNavigator.activate(deepLinkConfig)
        return true
    }
}
