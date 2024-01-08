package app.futured.kmptemplate.util.arch

import com.arkivanov.decompose.ComponentContext

/**
 * This interface is used to mark a class as a navigation entry.
 * The implementation of this interface wraps instance of a screen.
 */
interface NavEntry

/**
 * A base interface for destinations used in Decompose navigation.
 */
interface Destination<out C : NavEntry> {
    fun createComponent(componentContext: ComponentContext): C
}
