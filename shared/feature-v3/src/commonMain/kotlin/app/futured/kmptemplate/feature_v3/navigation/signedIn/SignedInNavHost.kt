package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.kmptemplate.feature_v3.navigation.home.HomeConfig
import app.futured.kmptemplate.feature_v3.navigation.home.HomeNavHost
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileConfig
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileNavHost
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

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


sealed interface SignedInChild {

    /**
     * Unique SwiftUI view identifier.
     *
     * On iOS, when displayed inside TabView, the view needs to have a unique ID assigned to it, so
     * when a child in the stack is replaced with new one (for example after opening deep link),
     * the SwiftUI knows to render the view inside TabView again.
     */
    abstract val iosViewId: String

    data class Home(
        val navHost: HomeNavHost,
        // TODO replace with UUID since Kotlin 2.0.20
        override val iosViewId: String = Clock.System.now().nanosecondsOfSecond.toString(),
    ) : SignedInChild

    data class Profile(
        val navHost: ProfileNavHost,
        // TODO replace with UUID since Kotlin 2.0.20
        override val iosViewId: String = Clock.System.now().nanosecondsOfSecond.toString(),
    ) : SignedInChild
}
