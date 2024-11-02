package app.futured.arkitekt.decompose.navigation

import com.arkivanov.decompose.GenericComponentContext

/**
 * A base interface for destinations used in Decompose navigation.
 */
interface Destination<out C : NavEntry, CTX: GenericComponentContext<CTX>> {
    fun createComponent(componentContext: CTX): C
}
