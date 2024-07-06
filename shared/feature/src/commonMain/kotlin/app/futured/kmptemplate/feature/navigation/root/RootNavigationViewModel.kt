package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkDestination
import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkResolver
import app.futured.kmptemplate.feature.navigation.signedin.SignedInDestination
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
internal class RootNavigationViewModel(
    private val deepLinkResolver: DeepLinkResolver,
    private val rootNavigator: RootNavigator,
    private val signedInNavigator: SignedInNavigator,
) : SharedViewModel<RootNavigationViewState, Nothing>(), RootNavigation.Actions {

    override val viewState: MutableStateFlow<RootNavigationViewState> = MutableStateFlow(RootNavigationViewState())
    private var pendingDeepLink: DeepLinkDestination? = null

    override fun openDeepLink(uri: String) {
        val deepLink = deepLinkResolver.resolve(uri) ?: return
        this.pendingDeepLink = deepLink

        /*
        You might wanna defer call to this function for later,
        for example when you need to load some data before navigating somewhere, etc.
         */
        consumeDeepLink()
    }

    private fun consumeDeepLink() {
        val deepLink = pendingDeepLink ?: return

        when (deepLink) {
            DeepLinkDestination.Login -> rootNavigator.setLogin()

            DeepLinkDestination.TabA -> rootNavigator.setSignedIn {
                signedInNavigator.setTab(SignedInDestination.A)
            }

            DeepLinkDestination.TabB -> rootNavigator.setSignedIn {
                signedInNavigator.setTab(SignedInDestination.B())
            }

            DeepLinkDestination.TabC -> rootNavigator.setSignedIn {
                signedInNavigator.setTab(SignedInDestination.C)
            }

            DeepLinkDestination.ThirdScreen -> rootNavigator.setSignedIn {
                signedInNavigator.setTab(SignedInDestination.B.deepLinkThirdScreen())
            }

            is DeepLinkDestination.SecretScreen -> rootNavigator.setSignedIn {
                signedInNavigator.setTab(SignedInDestination.B.deepLinkSecretScreen(deepLink.argument))
            }
        }

        this.pendingDeepLink = null
    }
}
