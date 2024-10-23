package app.futured.arkitekt.decompose.navigation

import app.futured.arkitekt.decompose.AppComponentContext

/**
 * A base interface for destinations used in Decompose navigation.
 */
interface Destination<out C : NavEntry> {
    fun createComponent(componentContext: AppComponentContext): C
}
