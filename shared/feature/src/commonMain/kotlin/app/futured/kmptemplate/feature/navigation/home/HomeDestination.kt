package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.first.FirstComponent
import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondComponent
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.third.ThirdComponent
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import app.futured.kmptemplate.util.arch.Component
import app.futured.kmptemplate.util.arch.Destination
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class HomeDestination : Parcelable, Destination<HomeEntry> {
    @Parcelize
    data class First(private val arg: String) : HomeDestination() {
        override fun createComponent(componentContext: ComponentContext): HomeEntry =
            HomeEntry.First(FirstComponent(componentContext))
    }

    @Parcelize
    data object Second : HomeDestination() {
        override fun createComponent(componentContext: ComponentContext): HomeEntry {
            return HomeEntry.Second(SecondComponent(componentContext))
        }
    }

    @Parcelize
    data object Third : HomeDestination() {
        override fun createComponent(componentContext: ComponentContext): HomeEntry {
            return HomeEntry.Third(ThirdComponent(componentContext))
        }
    }
}

sealed class HomeEntry : Component {
    data class First(val screen: FirstScreen) : HomeEntry()
    data class Second(val screen: SecondScreen) : HomeEntry()
    data class Third(val screen: ThirdScreen) : HomeEntry()
}
