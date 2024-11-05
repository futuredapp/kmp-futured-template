package app.futured.arkitekt.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.GenericComponentContext
import org.koin.core.component.KoinComponent

/**
 * An Arkitekt-specific [GenericComponentContext] decorated with [KoinComponent] that adds injection capabilities inside Components.
 * Any custom application-specific [ComponentContext] should implement this interface.
 */
interface ArkitektComponentContext<T : Any> : GenericComponentContext<T>, KoinComponent
