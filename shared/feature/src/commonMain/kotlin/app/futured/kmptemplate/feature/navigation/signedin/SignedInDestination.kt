package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.arkitekt.decompose.navigation.Destination
import app.futured.arkitekt.decompose.navigation.NavEntry
import app.futured.kmptemplate.feature.AppComponentContext
import app.futured.kmptemplate.feature.navigation.signedin.tab.a.TabANavigation
import app.futured.kmptemplate.feature.navigation.signedin.tab.a.TabANavigationComponent
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBDestination
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigation
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigationComponent
import app.futured.kmptemplate.feature.navigation.signedin.tab.c.TabCNavigation
import app.futured.kmptemplate.feature.navigation.signedin.tab.c.TabCNavigationComponent
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
sealed class SignedInDestination : Destination<SignedInNavEntry, AppComponentContext> {

    @Serializable
    data object A : SignedInDestination() {
        override fun createComponent(componentContext: AppComponentContext): SignedInNavEntry =
            SignedInNavEntry.A(TabANavigationComponent(componentContext))
    }

    @Serializable
    data class B(
        private val initialStack: List<TabBDestination> = listOf(TabBDestination.First),
        private val seed: Long = 0L, // Changing the seed ensures that entire navigation stack is regenerated, useful for when deep link is opened
    ) : SignedInDestination() {
        override fun createComponent(componentContext: AppComponentContext): SignedInNavEntry =
            SignedInNavEntry.B(TabBNavigationComponent(componentContext, initialStack))

        companion object {
            fun deepLinkThirdScreen() = B(
                initialStack = listOf(
                    TabBDestination.First,
                    TabBDestination.Second,
                    TabBDestination.Third,
                ),
                seed = Clock.System.now().toEpochMilliseconds(),
            )

            fun deepLinkSecretScreen(argument: String?) = B(
                initialStack = listOf(
                    TabBDestination.First,
                    TabBDestination.Secret(argument),
                ),
                seed = Clock.System.now().toEpochMilliseconds(),
            )
        }
    }

    @Serializable
    data object C : SignedInDestination() {
        override fun createComponent(componentContext: AppComponentContext): SignedInNavEntry =
            SignedInNavEntry.C(TabCNavigationComponent(componentContext))
    }
}

sealed class SignedInNavEntry : NavEntry {

    /**
     * Unique SwiftUI identifier.
     *
     * On iOS, when displayed inside TabView, the view needs to have a unique ID assigned to it, so
     * when a NavEntry in the stack is replaced with new one (for example after opening deep link),
     * the SwiftUI knows to render the view inside TabView again.
     */
    abstract val iosViewId: String

    data class A(
        val instance: TabANavigation,
        override val iosViewId: String = Clock.System.now().toEpochMilliseconds().toString(),
    ) : SignedInNavEntry()

    data class B(
        val instance: TabBNavigation,
        override val iosViewId: String = Clock.System.now().toEpochMilliseconds().toString(),
    ) : SignedInNavEntry()

    data class C(
        val instance: TabCNavigation,
        override val iosViewId: String = Clock.System.now().toEpochMilliseconds().toString(),
    ) : SignedInNavEntry()
}
