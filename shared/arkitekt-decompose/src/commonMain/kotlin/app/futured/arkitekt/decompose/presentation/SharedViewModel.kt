package app.futured.arkitekt.decompose.presentation

import app.futured.arkitekt.crusecases.scope.UseCaseExecutionScope
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Base class for ViewModels used in shared KMM module.
 *
 * The ViewModel implements an [InstanceKeeper.Instance] and can be retained
 * across configuration changes on Android using Decompose APIs.
 *
 * It implements the [UseCaseExecutionScope] interface, so KMM UseCases
 * can be executed in it's [CoroutineScope] tied to retained instance lifecycle.
 */
abstract class SharedViewModel<VS : ViewState, UI_EVENT : UiEvent<VS>> :
    InstanceKeeper.Instance,
    UseCaseExecutionScope,
    KoinComponent {

    abstract val viewState: MutableStateFlow<VS>

    // region Lifecycle

    override val viewModelScope: CoroutineScope = MainScope()

    override fun onDestroy() {
        viewModelScope.cancel()
    }

    // endregion

    // region Events

    private val uiEventChannel = Channel<UI_EVENT>(Channel.BUFFERED)

    /**
     * Hot buffered flow that emits UI events sent by [sendUiEvent].
     */
    val uiEvents: Flow<UI_EVENT> = uiEventChannel
        .receiveAsFlow()
        .shareIn(viewModelScope, SharingStarted.Lazily)

    /**
     * Sends an [UiEvent] to event channel that can be consumed using [uiEvents] flow.
     */
    fun sendUiEvent(event: UI_EVENT) {
        viewModelScope.launch {
            uiEventChannel.send(event)
        }
    }

    // endregion
}
