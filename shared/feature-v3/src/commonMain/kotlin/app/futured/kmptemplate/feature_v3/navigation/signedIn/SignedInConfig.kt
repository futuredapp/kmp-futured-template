package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.kmptemplate.feature_v3.navigation.home.HomeConfig
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileConfig
import app.futured.kmptemplate.feature_v3.ui.thirdScreen.ThirdScreenArgs
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
sealed interface SignedInConfig {

    companion object {

        private fun newSeed() = Clock.System.now().toEpochMilliseconds()

        fun deepLinkHome(): SignedInConfig = SignedInConfig.Home(
            initialStack = listOf(HomeConfig.First),
            seed = newSeed()
        )

        fun deepLinkProfile(): SignedInConfig = SignedInConfig.Profile(
            initialStack = listOf(ProfileConfig.Profile),
            seed = newSeed()
        )

        fun deepLinkSecondScreen(): SignedInConfig = SignedInConfig.Home(
            initialStack = listOf(HomeConfig.First, HomeConfig.Second),
            seed = newSeed()
        )

        fun deepLinkThirdScreen(args: ThirdScreenArgs) = SignedInConfig.Home(
            initialStack = listOf(HomeConfig.First, HomeConfig.Second, HomeConfig.Third(args)),
            seed = newSeed()
        )
    }

    @Serializable
    data class Home(
        val initialStack: List<HomeConfig> = listOf(HomeConfig.First),
        // Changing the seed ensures that entire navigation stack is regenerated, useful for when deep link is opened
        private val seed: Long = 0L,
    ) : SignedInConfig

    @Serializable
    data class Profile(
        val initialStack: List<ProfileConfig> = listOf(ProfileConfig.Profile),
        // Changing the seed ensures that entire navigation stack is regenerated, useful for when deep link is opened
        private val seed: Long = 0L,
    ) : SignedInConfig
}
