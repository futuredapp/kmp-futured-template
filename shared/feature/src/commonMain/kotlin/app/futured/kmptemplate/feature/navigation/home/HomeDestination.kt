package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.first.FirstComponent
import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondComponent
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.secret.SecretComponent
import app.futured.kmptemplate.feature.ui.secret.SecretScreen
import app.futured.kmptemplate.feature.ui.third.ThirdComponent
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import app.futured.kmptemplate.util.arch.Destination
import app.futured.kmptemplate.util.arch.NavEntry
import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.Serializable

@Serializable
sealed class HomeDestination : Destination<HomeEntry> {
    @Serializable
    data class First(private val arg: String) : HomeDestination() {
        override fun createComponent(componentContext: ComponentContext): HomeEntry =
            HomeEntry.First(FirstComponent(componentContext, arg))
    }

    @Serializable
    data object Second : HomeDestination() {
        override fun createComponent(componentContext: ComponentContext): HomeEntry {
            return HomeEntry.Second(SecondComponent(componentContext))
        }
    }

    @Serializable
    data object Third : HomeDestination() {
        override fun createComponent(componentContext: ComponentContext): HomeEntry {
            return HomeEntry.Third(ThirdComponent(componentContext))
        }
    }

    @Serializable
    data class Secret(val argument: String?) : HomeDestination() {
        override fun createComponent(componentContext: ComponentContext): HomeEntry {
            return HomeEntry.Secret(SecretComponent(argument))
        }
    }
}

sealed class HomeEntry : NavEntry {
    data class First(val screen: FirstScreen) : HomeEntry()
    data class Second(val screen: SecondScreen) : HomeEntry()
    data class Third(val screen: ThirdScreen) : HomeEntry()
    data class Secret(val screen: SecretScreen) : HomeEntry()
}
