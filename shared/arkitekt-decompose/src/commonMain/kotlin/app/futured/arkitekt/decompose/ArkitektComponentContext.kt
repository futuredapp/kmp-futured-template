package app.futured.arkitekt.decompose

import app.futured.arkitekt.decompose.presentation.SharedViewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools

/**
 * An Arkitekt-specific [GenericComponentContext] decorated with [KoinComponent] that adds injection capabilities inside Components.
 * Any custom application-specific [ComponentContext] should implement this interface.
 */
interface ArkitektComponentContext<T : Any> : GenericComponentContext<T>, KoinComponent

/**
 * Injects and retains instance of [SharedViewModel] inside [ArkitektComponentContext]'s [InstanceKeeperOwner].
 */
inline fun <reified T : SharedViewModel<*, *>> ArkitektComponentContext<*>.viewModel(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> = lazy(mode) { instanceKeeper.getOrCreate { get(qualifier, parameters) } }
