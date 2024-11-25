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
        val initialConfig: SignedInConfig = SignedInConfig.Home()
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

sealed interface RootChild {

    /**
     * Unique SwiftUI view identifier.
     *
     * Each view that can be replaced by deep link, must have an ID assigned to it.
     */
    abstract val iosViewId: String

    data class Login(
        val screen: LoginScreen,
        // TODO replace with UUID since Kotlin 2.0.20
        override val iosViewId: String = Clock.System.now().nanosecondsOfSecond.toString(),
    ) : RootChild

    data class SignedIn(
        val navHost: SignedInNavHost,
        // TODO replace with UUID since Kotlin 2.0.20
        override val iosViewId: String = Clock.System.now().nanosecondsOfSecond.toString(),
    ) : RootChild
}
