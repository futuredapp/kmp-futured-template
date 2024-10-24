package app.futured.kmptemplate.feature.navigation.root

import app.futured.arkitekt.decompose.navigation.Destination
import app.futured.arkitekt.decompose.navigation.NavEntry
import app.futured.kmptemplate.feature.AppComponentContext
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigation
import app.futured.kmptemplate.feature.navigation.signedin.SignedInNavigationComponent
import app.futured.kmptemplate.feature.ui.login.LoginComponent
import app.futured.kmptemplate.feature.ui.login.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class RootDestination : Destination<RootEntry, AppComponentContext> {

    @Serializable
    data object Login : RootDestination() {
        override fun createComponent(componentContext: AppComponentContext): RootEntry =
            RootEntry.Login(LoginComponent(componentContext))
    }

    @Serializable
    data object SignedIn : RootDestination() {
        override fun createComponent(componentContext: AppComponentContext): RootEntry =
            RootEntry.SignedIn(SignedInNavigationComponent(componentContext))
    }
}

sealed class RootEntry : NavEntry {
    data class Login(val instance: LoginScreen) : RootEntry()
    data class SignedIn(val instance: SignedInNavigation) : RootEntry()
}
