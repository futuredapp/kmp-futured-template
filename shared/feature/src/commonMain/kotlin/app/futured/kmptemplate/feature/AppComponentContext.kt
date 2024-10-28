package app.futured.kmptemplate.feature

import app.futured.arkitekt.decompose.ArkitektComponentContext
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.statekeeper.StateKeeperOwner

/**
 * Application-specific [ComponentContext] that can be decorated with custom functionality.
 * See [docs](https://arkivanov.github.io/Decompose/component/custom-component-context/) for more info.
 */
interface AppComponentContext : ArkitektComponentContext<AppComponentContext>

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
