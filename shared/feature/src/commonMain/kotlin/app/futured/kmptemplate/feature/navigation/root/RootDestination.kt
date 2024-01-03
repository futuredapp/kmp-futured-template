package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.home.HomeNavigation
import app.futured.kmptemplate.feature.navigation.home.HomeNavigationComponent
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
    data object Home : RootDestination() {
        override fun createComponent(componentContext: ComponentContext): RootEntry =
            RootEntry.Home(HomeNavigationComponent(componentContext))
    }
}

sealed class RootEntry : NavEntry {
    data class Login(val screen: LoginScreen) : RootEntry()
    data class Home(val navigation: HomeNavigation) : RootEntry()
}
