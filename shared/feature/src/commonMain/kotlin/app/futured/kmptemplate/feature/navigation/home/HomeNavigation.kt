package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface HomeNavigation {
    val stack: Value<ChildStack<HomeDestination, HomeNavigationEntry>>
}

sealed class HomeDestination : Parcelable {
    @Parcelize
    data object First : HomeDestination()

    @Parcelize
    data object Second : HomeDestination()

    @Parcelize
    data object Third : HomeDestination()
}

interface HomeNavigationEntry

interface Destination {

}

interface Sheet : Destination {

}

interface Dialog: Destination {

}

class Router() {
    val stack: Value<ChildStack<HomeDestination, HomeNavigationEntry>>
}