package app.futured.arkitekt.decompose.injection

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
 * Base [GenericComponentContext] decorated with [KoinComponent] that allows Components to inject ViewModels.
 * Any custom application-specific [ComponentContext] should implement this interface.
 */
interface KoinComponentContext<T : Any> : GenericComponentContext<T>, KoinComponent

/**
 * Injects and retains instance of [SharedViewModel] inside [KoinComponentContext]'s [InstanceKeeperOwner].
 */
inline fun <reified T : SharedViewModel<*, *>> KoinComponentContext<*>.viewModel(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> = lazy(mode) { instanceKeeper.getOrCreate { get(qualifier, parameters) } }
