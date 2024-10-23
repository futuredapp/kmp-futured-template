package app.futured.kmptemplate.feature.ui.first

import app.futured.arkitekt.crusecases.scope.UseCaseExecutionScope
import app.futured.kmptemplate.feature.domain.CounterUseCase
import app.futured.kmptemplate.feature.domain.CounterUseCaseArgs
import app.futured.kmptemplate.feature.domain.SyncDataUseCase
import app.futured.kmptemplate.feature.ui.picker.PickerResult
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.arch.UiEvent
import app.futured.kmptemplate.util.arch.ViewState
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
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
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import kotlin.time.Duration.Companion.milliseconds

@Factory
internal class FirstComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val navigation: FirstNavigationActions,
    private val syncDataUseCase: SyncDataUseCase,
    private val counterUseCase: CounterUseCase,
) : AppComponent<FirstViewState, FirstUiEvent>(componentContext, FirstViewState()),
    FirstScreen {

    private val logger = Logger.withTag("FirstComponent")

    override fun onStart() {
        syncData()
        observeCounter()
    }

    override val actions: FirstScreen.Actions = object : FirstScreen.Actions {
        override fun onBackClick() = navigation.pop()
        override fun onNextClick() = navigation.navigateToSecond()
        override fun onPickerClick() = navigation.showPicker()
        override fun onPickerResult(result: PickerResult) = updatePickerResult(result)
    }

    private fun syncData() = syncDataUseCase.execute {
        onError { error ->
            logger.e(error) { error.message.toString() }
        }
    }

    private fun observeCounter() = counterUseCase.execute(CounterUseCaseArgs(interval = 200.milliseconds)) {
        onNext { count ->
            updateCount(count)

            if (count == 10L) {
                logger.d { "Conter reached 10" }
                sendUiEvent(FirstUiEvent.ShowToast("Counter reached 10 🎉"))
            }
        }
        onError { error ->
            logger.e(error) { "Counter error" }
        }
    }

    private fun updateCount(count: Long) {
        updateState {
            copy(
                text = StringDesc.ResourceFormatted(
                    stringRes = MR.strings.first_screen_text,
                    1,
                    count,
                ),
            )
        }
    }

    private fun updatePickerResult(result: PickerResult) {
        when (result) {
            is PickerResult.Pick -> updateState {
                copy(pickerResult = result.id)
            }

            PickerResult.Dismissed -> updateState {
                copy(pickerResult = "dismissed")
            }
        }
    }
}

abstract class AppComponent<VS : ViewState, E : UiEvent<VS>>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : BaseComponent<VS, E, AppComponentContext>(componentContext, defaultState)

abstract class BaseComponent<VS : ViewState, E : UiEvent<VS>, CTX : GenericComponentContext<CTX>>(
    componentContext: CTX,
    defaultState: VS,
) : GenericComponentContext<CTX> by componentContext,
    UseCaseExecutionScope {

    // region Lifecycle

    private val componentCoroutineScope = MainScope().also { scope ->
        lifecycle.doOnDestroy { scope.cancel() }
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

class KoinAppComponentFactory(val context: AppComponentContext) : KoinComponent {

    inline fun <reified C : AppComponent<*, *>> createComponent(
        vararg parameters: Any?,
    ): C = get(
        qualifier = null,
        parameters = {
            parametersOf(
                context,
                *parameters,
            )
        },
    )
}
