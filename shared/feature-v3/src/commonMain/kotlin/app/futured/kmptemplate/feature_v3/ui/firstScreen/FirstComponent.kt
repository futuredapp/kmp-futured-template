package app.futured.kmptemplate.feature_v3.ui.firstScreen

import app.futured.kmptemplate.feature_v3.domain.CounterUseCase
import app.futured.kmptemplate.feature_v3.domain.CounterUseCaseArgs
import app.futured.kmptemplate.feature_v3.domain.SyncDataUseCase
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import app.futured.kmptemplate.resources.MR
import co.touchlab.kermit.Logger
import dev.icerock.moko.resources.format
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import kotlin.time.Duration.Companion.milliseconds

@Factory
internal class FirstComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: FirstScreenNavigation,
    private val syncDataUseCase: SyncDataUseCase,
    private val counterUseCase: CounterUseCase,
) : ScreenComponent<FirstViewState, FirstUiEvent, FirstScreenNavigation>(
    componentContext = componentContext,
    defaultState = FirstViewState(),
), FirstScreen {

    companion object {
        private const val COUNTER_ALERT = 10L
    }

    private val logger = Logger.withTag("FirstComponent")

    override val viewState: StateFlow<FirstViewState> = componentState.whenStarted {
        syncData()
        observeCounter()
    }

    override val actions: FirstScreen.Actions = object : FirstScreen.Actions {
        override fun onNext() = navigation.toSecond()
    }

    private fun syncData() = syncDataUseCase.execute {
        onError { error ->
            logger.e(error) { error.message.toString() }
        }
    }

    private fun observeCounter() = counterUseCase.execute(CounterUseCaseArgs(interval = 200.milliseconds)) {
        onNext { count ->
            updateCount(count)

            if (count == COUNTER_ALERT) {
                logger.d { "Counter reached 10" }
                sendUiEvent(FirstUiEvent.ShowToast(MR.strings.first_screen_counter_alert.format(COUNTER_ALERT)))
            }
        }
        onError { error ->
            logger.e(error) { "Counter error" }
        }
    }

    private fun updateCount(count: Long) {
        updateState { copy(text = MR.strings.first_screen_counter.format(count)) }
    }
}
