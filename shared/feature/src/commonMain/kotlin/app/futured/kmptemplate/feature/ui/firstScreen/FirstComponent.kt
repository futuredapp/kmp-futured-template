package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.CounterUseCase
import app.futured.kmptemplate.feature.domain.CounterUseCaseArgs
import app.futured.kmptemplate.feature.domain.SyncDataUseCase
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.datetime.desc
import co.touchlab.kermit.Logger
import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.icerock.moko.resources.format
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import kotlin.time.Duration.Companion.milliseconds

@Factory
@GenerateFactory
internal class FirstComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: FirstScreenNavigation,
    private val syncDataUseCase: SyncDataUseCase,
    private val counterUseCase: CounterUseCase,
) : ScreenComponent<FirstViewState, FirstUiEvent, FirstScreenNavigation>(
    componentContext = componentContext,
    defaultState = FirstViewState(),
),
    FirstScreen,
    FirstScreenNavigation by navigation,
    FirstScreen.Actions {

    companion object {
        private const val COUNTER_ALERT_AT_SECONDS = 30L
    }

    private val logger = Logger.withTag("FirstComponent")

    override val viewState: StateFlow<FirstViewState> = componentState.asStateFlow()
    override val actions: FirstScreen.Actions = this

    init {
        doOnCreate {
            syncData()
            observeCounter()
            updateCreatedAtTimestamp()
        }
    }

    override fun onNext() = navigateToSecond()

    private fun syncData() = syncDataUseCase.execute {
        onSuccess { person ->
            componentState.update { it.copy(randomPerson = MR.strings.first_screen_random_person.format(person.name.orEmpty())) }
        }
        onError { error ->
            componentState.update { it.copy(randomPerson = MR.strings.first_screen_random_person.format("Failed to fetch")) }
            logger.e(error) { error.message.toString() }
        }
    }

    private fun observeCounter() = counterUseCase.execute(CounterUseCaseArgs(interval = 200.milliseconds)) {
        onNext { count ->
            updateCount(count)

            if (count == COUNTER_ALERT_AT_SECONDS) {
                logger.d { "Counter reached 10" }
                sendUiEvent(FirstUiEvent.ShowToast(MR.strings.first_screen_counter_alert.format(COUNTER_ALERT_AT_SECONDS)))
            }
        }
        onError { error ->
            logger.e(error) { "Counter error" }
        }
    }

    private fun updateCount(count: Long) {
        componentState.update { it.copy(counter = MR.strings.first_screen_counter.format(count)) }
    }

    private fun updateCreatedAtTimestamp() {
        componentState.update {
            it.copy(createdAt = MR.strings.first_screen_created_at.format(Clock.System.now().desc("Hms")))
        }
    }
}
