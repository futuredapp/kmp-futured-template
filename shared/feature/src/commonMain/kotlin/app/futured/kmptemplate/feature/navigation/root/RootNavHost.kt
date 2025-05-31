package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.home.HomeConfig
import app.futured.kmptemplate.feature.navigation.home.HomeNavHost
import app.futured.kmptemplate.feature.ui.welcomeScreen.WelcomeScreen
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
        fun updateCameraPermission(allowed: Boolean)
    }
}

@Serializable
sealed interface RootConfig {

    @Serializable
    data object Intro : RootConfig

    @Serializable
    data class Home(
        // Changing the seed ensures that entire navigation tree is regenerated. Useful for when deep link is opened.
        private val seed: Long = 0L,
        val initialStack: List<HomeConfig> = listOf(HomeConfig.First)
    ) : RootConfig

    companion object {

        private fun newSeed() = Clock.System.now().toEpochMilliseconds()

        fun deepLinkHome(): RootConfig = Home(
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

    data class Intro(val screen: WelcomeScreen, override val iosViewId: String = Uuid.random().toString()) : RootChild

    data class Home(val navHost: HomeNavHost, override val iosViewId: String = Uuid.random().toString()) : RootChild
}
