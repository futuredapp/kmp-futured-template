package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.kmptemplate.feature_v3.navigation.home.HomeConfig
import app.futured.kmptemplate.feature_v3.navigation.home.HomeNavHost
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileConfig
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileNavHost
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SignedInNavHost : BackHandlerOwner {

    val stack: StateFlow<ChildStack<SignedInConfig, SignedInChild>>
    val viewState: StateFlow<SignedInNavHostViewState>

    val actions: Actions

    /*
    These are direct handles to navigation tabs on iOS
     */
    val homeTab: StateFlow<SignedInChild.Home?>
    val profileTab: StateFlow<SignedInChild.Profile?>

    interface Actions {
        fun onTabSelected(tab: NavigationTab)
        fun onBack()
    }
}

@Serializable
sealed interface SignedInConfig {

    @Serializable
    data class Home(
        val initialStack: List<HomeConfig> = listOf(HomeConfig.First),
    ) : SignedInConfig

    @Serializable
    data class Profile(
        val initialStack: List<ProfileConfig> = listOf(ProfileConfig.Profile),
    ) : SignedInConfig
}

@OptIn(ExperimentalUuidApi::class)
sealed interface SignedInChild {

    /**
     * Unique SwiftUI view identifier.
     * Each view that can get replaced by deep link, must have a unique ID assigned to it.
     * (Otherwise, the view will lose the state and become unresponsive).
     */
    val iosViewId: String

    data class Home(
        val navHost: HomeNavHost,
        override val iosViewId: String = Uuid.random().toString(),
    ) : SignedInChild

    data class Profile(
        val navHost: ProfileNavHost,
        override val iosViewId: String = Uuid.random().toString(),
    ) : SignedInChild
}
