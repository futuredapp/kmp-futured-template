package app.futured.kmptemplate.feature_v3.navigation.root

import app.futured.kmptemplate.feature_v3.navigation.home.HomeConfig
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileConfig
import app.futured.kmptemplate.feature_v3.navigation.signedIn.SignedInConfig
import app.futured.kmptemplate.feature_v3.navigation.signedIn.SignedInNavHost
import app.futured.kmptemplate.feature_v3.ui.loginScreen.LoginScreen
import app.futured.kmptemplate.feature_v3.ui.thirdScreen.ThirdScreenArgs
import com.arkivanov.decompose.router.slot.ChildSlot
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface RootNavHost {
    val slot: StateFlow<ChildSlot<RootConfig, RootChild>>
    val actions: Actions

    interface Actions {
        fun onDeepLink(uri: String)
    }
}

@Serializable
sealed interface RootConfig {

    @Serializable
    data object Login : RootConfig

    @Serializable
    data class SignedIn(
        // Changing the seed ensures that entire navigation tree is regenerated. Useful for when deep link is opened.
        private val seed: Long = 0L,
        val initialConfig: SignedInConfig = SignedInConfig.Home(),
    ) : RootConfig

    companion object {

        private fun newSeed() = Clock.System.now().toEpochMilliseconds()

        fun deepLinkHome(): RootConfig = RootConfig.SignedIn(
            initialConfig = SignedInConfig.Home(
                initialStack = listOf(HomeConfig.First),
            ),
            seed = newSeed(),
        )

        fun deepLinkProfile(): RootConfig = RootConfig.SignedIn(
            initialConfig = SignedInConfig.Profile(
                initialStack = listOf(ProfileConfig.Profile),
            ),
            seed = newSeed(),
        )

        fun deepLinkSecondScreen(): RootConfig = RootConfig.SignedIn(
            initialConfig = SignedInConfig.Home(
                initialStack = listOf(HomeConfig.First, HomeConfig.Second),
            ),
            seed = newSeed(),
        )

        fun deepLinkThirdScreen(args: ThirdScreenArgs): RootConfig = RootConfig.SignedIn(
            initialConfig = SignedInConfig.Home(
                initialStack = listOf(HomeConfig.First, HomeConfig.Second, HomeConfig.Third(args)),
            ),
            seed = newSeed(),
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
sealed interface RootChild {

    /**
     * Unique SwiftUI view identifier.
     * Each view that can get replaced by deep link, must have a unique ID assigned to it.
     * (Otherwise, the view will lose the state and become unresponsive).
     */
    val iosViewId: String

    data class Login(
        val screen: LoginScreen,
        override val iosViewId: String = Uuid.random().toString(),
    ) : RootChild

    data class SignedIn(
        val navHost: SignedInNavHost,
        override val iosViewId: String = Uuid.random().toString(),
    ) : RootChild
}
