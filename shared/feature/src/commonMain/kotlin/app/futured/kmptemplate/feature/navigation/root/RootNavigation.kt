package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.home.HomeNavigation
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface RootNavigation {
    val slot: Value<ChildSlot<RootDestination, RootNavigationEntry>>
}

sealed class RootDestination : Parcelable {

    @Parcelize
    data object Login : RootDestination()

    @Parcelize
    data object Home : RootDestination()
}

sealed class RootNavigationEntry {
    data object Login : RootNavigationEntry()
    data class Home(val navigation: HomeNavigation) : RootNavigationEntry()
}
