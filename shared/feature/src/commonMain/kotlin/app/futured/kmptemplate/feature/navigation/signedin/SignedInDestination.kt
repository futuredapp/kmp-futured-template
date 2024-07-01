package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.feature.navigation.signedin.tab.a.TabANavigation
import app.futured.kmptemplate.feature.navigation.signedin.tab.a.TabANavigationComponent
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBDestination
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigation
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigationComponent
import app.futured.kmptemplate.feature.navigation.signedin.tab.c.TabCNavigation
import app.futured.kmptemplate.feature.navigation.signedin.tab.c.TabCNavigationComponent
import app.futured.kmptemplate.util.arch.Destination
import app.futured.kmptemplate.util.arch.NavEntry
import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.Serializable

@Serializable
sealed class SignedInDestination : Destination<SignedInNavEntry> {

    @Serializable
    data object A : SignedInDestination() {
        override fun createComponent(componentContext: ComponentContext): SignedInNavEntry =
            SignedInNavEntry.A(TabANavigationComponent(componentContext))
    }

    @Serializable
    data class B(private val initialStack: List<TabBDestination> = listOf(TabBDestination.First)) : SignedInDestination() {
        override fun createComponent(componentContext: ComponentContext): SignedInNavEntry =
            SignedInNavEntry.B(TabBNavigationComponent(componentContext, initialStack))
    }

    @Serializable
    data object C : SignedInDestination() {
        override fun createComponent(componentContext: ComponentContext): SignedInNavEntry =
            SignedInNavEntry.C(TabCNavigationComponent(componentContext))
    }
}

sealed class SignedInNavEntry : NavEntry {
    data class A(val instance: TabANavigation) : SignedInNavEntry()
    data class B(val instance: TabBNavigation) : SignedInNavEntry()
    data class C(val instance: TabCNavigation) : SignedInNavEntry()
}
