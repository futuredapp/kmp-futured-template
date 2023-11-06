package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.home.HomeNavigation
import app.futured.kmptemplate.feature.ui.login.LoginScreen
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.StateFlow

interface RootNavigation {
    val slot: StateFlow<ChildSlot<RootDestination, RootNavigationEntry>>
}

sealed class RootDestination : Parcelable {

    @Parcelize
    data object Login : RootDestination()

    @Parcelize
    data object Home : RootDestination()
}

sealed class RootNavigationEntry {
    data class Login(val screen: LoginScreen) : RootNavigationEntry()
    data class Home(val navigation: HomeNavigation) : RootNavigationEntry()
}
