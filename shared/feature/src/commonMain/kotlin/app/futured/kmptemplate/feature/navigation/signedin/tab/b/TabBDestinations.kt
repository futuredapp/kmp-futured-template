package app.futured.kmptemplate.feature.navigation.signedin.tab.b

import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.secret.SecretScreen
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import app.futured.kmptemplate.util.arch.NavEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class TabBDestination {
    @Serializable
    data object First : TabBDestination()

    @Serializable
    data object Second : TabBDestination()

    @Serializable
    data object Third : TabBDestination()

    @Serializable
    data class Secret(val argument: String?) : TabBDestination()
}

sealed class TabBNavEntry : NavEntry {
    data class First(val instance: FirstScreen) : TabBNavEntry()
    data class Second(val instance: SecondScreen) : TabBNavEntry()
    data class Third(val instance: ThirdScreen) : TabBNavEntry()
    data class Secret(val instance: SecretScreen) : TabBNavEntry()
}
