package app.futured.arkitekt.decompose.presentation

import app.futured.arkitekt.crusecases.scope.UseCaseExecutionScope
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Base class for all Components in architecture.
 * The BaseComponent allows implementation of stateful screen / navigation host Components which perform some presentation logic.
 *
 * @param VS The type of the component state.
 * @param E The type of the UI events.
 * @param componentContext The context of the component.
 * @param defaultState The default Component state.
 * @param lifecycleScope The coroutine scope tied to the lifecycle of the component.
 * It will be automatically cancelled when component's lifecycle is destroyed.
 * @param useCaseDispatcher A [CoroutineDispatcher] for executing UseCases in [UseCaseExecutionScope].
 */
abstract class BaseComponent<VS : Any, E : Any>(
    componentContext: GenericComponentContext<*>,
    private val defaultState: VS,
    open val lifecycleScope: CoroutineScope = MainScope(),
    open val useCaseDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : UseCaseExecutionScope {

    init {
        componentContext.lifecycle.doOnDestroy {
            lifecycleScope.cancel()
        }
    }

    /**
     * An internal state of the component of type [VS].
     */
    protected val componentState: MutableStateFlow<VS> = MutableStateFlow(defaultState)

    // region UI events

    /**
     * Internal flow for sending UI events.
     */
    private val eventFlow = MutableSharedFlow<E>()

    /**
     * Flow of UI events.
     */
    val events: Flow<E>
        get() = eventFlow

    // endregion

    // region UseCaseExecutionScope

    override val useCaseJobPool: MutableMap<Any, Job> = mutableMapOf()

    override val useCaseScope: CoroutineScope
        get() = lifecycleScope

    override fun getWorkerDispatcher(): CoroutineDispatcher = useCaseDispatcher

    // endregion

    // region Implementation API

    /**
     * Sends a UI event.
     *
     * @param event The event to send.
     */
    protected fun sendEvent(event: E) {
        lifecycleScope.launch {
            eventFlow.emit(event)
        }
    }

    // endregion
}
