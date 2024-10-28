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
import kotlinx.coroutines.launch

/**
 * TODO KDoc
 */
abstract class BaseComponent<VS : ViewState, E : UiEvent<VS>>(
    componentContext: GenericComponentContext<*>,
    defaultState: VS,
) : UseCaseExecutionScope {

    // region Lifecycle

    private val componentCoroutineScope = MainScope().also { scope ->
        componentContext.lifecycle.doOnDestroy { scope.cancel() }
    }

    private val state: MutableStateFlow<VS> = MutableStateFlow(defaultState)
    val viewState: StateFlow<VS> = state
        .onStart { onStart() }
        .stateIn(componentCoroutineScope, SharingStarted.Lazily, defaultState)

    // region UI events

    private val uiEventChannel = Channel<E>(Channel.BUFFERED)

    val events: Flow<E> = uiEventChannel
        .receiveAsFlow()
        .shareIn(componentCoroutineScope, SharingStarted.Lazily)

    // endregion

    // endregion

    // region UseCaseExecutionScope

    override val viewModelScope: CoroutineScope = componentCoroutineScope

    // endregion

    // region Implementation API

    protected abstract fun onStart()

    protected fun updateState(update: VS.() -> VS) {
        state.value = update(state.value)
    }

    protected fun sendUiEvent(event: E) {
        componentCoroutineScope.launch {
            uiEventChannel.send(event)
        }
    }

    // endregion
}
