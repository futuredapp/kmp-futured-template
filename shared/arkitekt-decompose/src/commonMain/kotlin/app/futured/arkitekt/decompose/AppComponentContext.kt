package app.futured.arkitekt.decompose

import app.futured.arkitekt.decompose.presentation.SharedViewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.statekeeper.StateKeeperOwner
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools

/**
 * Application-specific [ComponentContext].
 *
 * Decorates [KoinComponent] with [KoinComponent], allowing injection in components by default.
 */
interface AppComponentContext : GenericComponentContext<AppComponentContext>, KoinComponent

/**
 * Default implementation of [AppComponentContext].
 *
 * @param componentContext [ComponentContext] to wrap.
 */
class DefaultAppComponentContext(
    componentContext: ComponentContext,
) : AppComponentContext,
    LifecycleOwner by componentContext,
    StateKeeperOwner by componentContext,
    InstanceKeeperOwner by componentContext,
    BackHandlerOwner by componentContext {

    override val componentContextFactory: ComponentContextFactory<AppComponentContext> =
        ComponentContextFactory { lifecycle, stateKeeper, instanceKeeper, backHandler ->
            val ctx = componentContext.componentContextFactory(lifecycle, stateKeeper, instanceKeeper, backHandler)
            DefaultAppComponentContext(ctx)
        }
}

/**
 * Injects and retains instance of [SharedViewModel] inside [AppComponentContext]'s [InstanceKeeperOwner].
 */
inline fun <reified T : SharedViewModel<*, *>> AppComponentContext.viewModel(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> = lazy(mode) { instanceKeeper.getOrCreate { get(qualifier, parameters) } }
