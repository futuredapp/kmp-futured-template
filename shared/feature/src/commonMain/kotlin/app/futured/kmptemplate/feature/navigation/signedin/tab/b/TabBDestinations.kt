package app.futured.kmptemplate.feature.navigation.signedin.tab.b

import app.futured.kmptemplate.feature.ui.first.FirstComponent
import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondComponent
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.secret.SecretComponent
import app.futured.kmptemplate.feature.ui.secret.SecretScreen
import app.futured.kmptemplate.feature.ui.third.ThirdComponent
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.arch.Destination
import app.futured.kmptemplate.util.arch.NavEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class TabBDestination : Destination<TabBNavEntry> {
    @Serializable
    data object First : TabBDestination() {
        override fun createComponent(componentContext: AppComponentContext): TabBNavEntry =
            TabBNavEntry.First(FirstComponent(componentContext))
    }

    @Serializable
    data object Second : TabBDestination() {
        override fun createComponent(componentContext: AppComponentContext): TabBNavEntry {
            return TabBNavEntry.Second(SecondComponent(componentContext))
        }
    }

    @Serializable
    data object Third : TabBDestination() {
        override fun createComponent(componentContext: AppComponentContext): TabBNavEntry {
            return TabBNavEntry.Third(ThirdComponent(componentContext))
        }
    }

    @Serializable
    data class Secret(val argument: String?) : TabBDestination() {
        override fun createComponent(componentContext: AppComponentContext): TabBNavEntry {
            return TabBNavEntry.Secret(SecretComponent(argument))
        }
    }
}

sealed class TabBNavEntry : NavEntry {
    data class First(val instance: FirstScreen) : TabBNavEntry()
    data class Second(val instance: SecondScreen) : TabBNavEntry()
    data class Third(val instance: ThirdScreen) : TabBNavEntry()
    data class Secret(val instance: SecretScreen) : TabBNavEntry()
}
