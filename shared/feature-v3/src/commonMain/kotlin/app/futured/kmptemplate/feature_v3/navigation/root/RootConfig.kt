package app.futured.kmptemplate.feature_v3.navigation.root

import app.futured.kmptemplate.feature_v3.navigation.home.HomeConfig
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileConfig
import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {

    @Serializable
    data class Home(
        val initialStack: List<HomeConfig> = listOf(HomeConfig.First),
        // Changing the seed ensures that entire navigation stack is regenerated, useful for when deep link is opened
        private val seed: Long = 0L,
    ) : RootConfig

    @Serializable
    data class Profile(
        val initialStack: List<ProfileConfig> = listOf(ProfileConfig.Profile),
        // Changing the seed ensures that entire navigation stack is regenerated, useful for when deep link is opened
        private val seed: Long = 0L,
    ) : RootConfig
}
