package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.kmptemplate.feature_v3.navigation.home.HomeNavHost
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileNavHost
import kotlinx.datetime.Clock

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
        override val iosViewId: String = Clock.System.now().nanosecondsOfSecond.toString(), // TODO replace with UUID since Kotlin 2.0.20
    ) : SignedInChild

    data class Profile(
        val navHost: ProfileNavHost,
        override val iosViewId: String = Clock.System.now().nanosecondsOfSecond.toString(), // TODO replace with UUID since Kotlin 2.0.20
    ) : SignedInChild
}
