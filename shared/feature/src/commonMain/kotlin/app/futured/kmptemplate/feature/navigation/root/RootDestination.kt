package app.futured.kmptemplate.feature.navigation.root

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
    data object SignedIn : RootDestination() {
        override fun createComponent(componentContext: ComponentContext): RootEntry =
            RootEntry.SignedIn(SignedInNavigationComponent(componentContext))
    }
}

sealed class RootEntry : NavEntry {
    data class Login(val instance: LoginScreen) : RootEntry()
    data class SignedIn(val instance: SignedInNavigation) : RootEntry()
}
