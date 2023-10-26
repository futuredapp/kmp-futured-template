package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface HomeNavigation {
    val stack: Value<ChildStack<HomeDestination, HomeNavigationEntry>>
    val actions: Actions

    interface Actions {
        fun iosPopTo(newStack: List<Child<HomeDestination, HomeNavigationEntry>>)
    }
}

sealed class HomeDestination : Parcelable {
    @Parcelize
    data object First : HomeDestination()

    @Parcelize
    data object Second : HomeDestination()

    @Parcelize
    data object Third : HomeDestination()
}

sealed class HomeNavigationEntry {
    data class First(val screen: FirstScreen) : HomeNavigationEntry()
    data class Second(val screen: SecondScreen) : HomeNavigationEntry()
    data class Third(val screen: ThirdScreen) : HomeNavigationEntry()
}
