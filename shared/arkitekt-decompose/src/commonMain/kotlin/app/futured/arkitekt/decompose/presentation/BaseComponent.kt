package app.futured.arkitekt.decompose.presentation

import app.futured.arkitekt.crusecases.scope.CoroutineScopeOwner
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
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
 * @param useCaseDispatcher A [CoroutineDispatcher] for executing UseCases in [CoroutineScopeOwner].
 */
abstract class BaseComponent<VS : Any, E : Any>(
    componentContext: GenericComponentContext<*>,
    private val defaultState: VS,
    open val lifecycleScope: CoroutineScope = MainScope(),
    open val useCaseDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : CoroutineScopeOwner {

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
     * Channel for sending UI events.
     */
    private val uiEventChannel = Channel<E>(Channel.BUFFERED)

    /**
     * Flow of UI events.
     */
    val events: Flow<E> = uiEventChannel.receiveAsFlow()
        .shareIn(lifecycleScope, SharingStarted.Lazily)

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
            uiEventChannel.send(event)
        }
    }

    // endregion
}
