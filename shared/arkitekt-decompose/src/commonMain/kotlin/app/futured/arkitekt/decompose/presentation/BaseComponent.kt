package app.futured.arkitekt.decompose.presentation

import app.futured.arkitekt.crusecases.scope.UseCaseExecutionScope
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * TODO KDoc
 */
abstract class BaseComponent<VS : Any, E : Any>(
    componentContext: GenericComponentContext<*>,
    private val defaultState: VS,
) : UseCaseExecutionScope {

    // region Lifecycle

    protected val componentCoroutineScope = MainScope().also { scope ->
        componentContext.lifecycle.doOnDestroy { scope.cancel() }
    }

    protected val componentState: MutableStateFlow<VS> = MutableStateFlow(defaultState)

    /**
     * TODO KDoc
     */
    fun Flow<VS>.whenStarted(started: SharingStarted = SharingStarted.Lazily, onStart: () -> Unit = {}): StateFlow<VS> =
        onStart { onStart() }.stateIn(componentCoroutineScope, started, defaultState)

    // endregion

    // region UI events

    private val uiEventChannel = Channel<E>(Channel.BUFFERED)

    val events: Flow<E> = uiEventChannel
        .receiveAsFlow()
        .shareIn(componentCoroutineScope, SharingStarted.Lazily)

    // endregion

    // region UseCaseExecutionScope

    override val viewModelScope: CoroutineScope = componentCoroutineScope

    // endregion

    // region Implementation API

    protected fun updateState(update: VS.() -> VS) {
        componentState.update(update)
    }

    protected fun sendUiEvent(event: E) {
        componentCoroutineScope.launch {
            uiEventChannel.send(event)
        }
    }

    // endregion
}
