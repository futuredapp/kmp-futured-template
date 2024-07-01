package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.signedin.SignedInDestination
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigation
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigationComponent
import app.futured.kmptemplate.feature.ui.login.LoginComponent
import app.futured.kmptemplate.feature.ui.login.LoginScreen
import app.futured.kmptemplate.util.arch.Destination
import app.futured.kmptemplate.util.arch.NavEntry
import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.Serializable

@Serializable
sealed class RootDestination : Destination<RootEntry> {

    @Serializable
    data object Login : RootDestination() {
        override fun createComponent(componentContext: ComponentContext): RootEntry =
            RootEntry.Login(LoginComponent(componentContext))
    }

    @Serializable
    data class SignedIn(
        val initialStack: List<SignedInDestination> = RootDestinationDefaults.getInitialSignedInStack(),
    ) : RootDestination() {
        override fun createComponent(componentContext: ComponentContext): RootEntry =
            RootEntry.SignedIn(SignedInNavigationComponent(componentContext, initialStack))
    }
}

sealed class RootEntry : NavEntry {
    data class Login(val instance: LoginScreen) : RootEntry()
    data class SignedIn(val instance: SignedInNavigation) : RootEntry()
}

expect object RootDestinationDefaults {

    /**
     * Returns initial stack for [RootDestination.SignedIn] destination.
     *
     * On Android this can be a single destination, others will be created lazily as user taps on bottom navigation items.
     *
     * On iOS, this must be a complete list of all possible bottom navigation tabs,
     * last being the one on top of the stack = default selected.
     */
    fun getInitialSignedInStack(): List<SignedInDestination>
}
