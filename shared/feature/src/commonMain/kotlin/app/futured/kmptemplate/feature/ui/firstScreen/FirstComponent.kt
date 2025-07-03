package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.CounterUseCase
import app.futured.kmptemplate.feature.domain.FetchDataUseCase
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import co.touchlab.kermit.Logger
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import kotlin.time.Duration.Companion.milliseconds

@Factory
@GenerateFactory
internal class FirstComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: FirstScreenNavigation,
    private val fetchDataUseCase: FetchDataUseCase,
    private val counterUseCase: CounterUseCase,
    override val lifecycleScope: CoroutineScope = MainScope(),
    override val useCaseDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ScreenComponent<FirstViewState, FirstUiEvent, FirstScreenNavigation>(
    componentContext = componentContext,
    defaultState = FirstViewState(),
),
    FirstScreen,
    FirstScreenNavigation by navigation,
    FirstScreen.Actions {

    companion object {
        private const val COUNTER_ALERT = 30L
    }

    private val logger = Logger.withTag("FirstComponent")

    override val viewState: StateFlow<FirstViewState> = componentState
    override val actions: FirstScreen.Actions = this

    init {
        doOnCreate {
            syncData()
            observeCounter()
        }
    }

    override fun onNext() = navigateToSecond()

    private fun syncData() = fetchDataUseCase.execute {
        onSuccess { person ->
            componentState.update { it.copy(randomPerson = person) }
        }
        onError { error ->
            componentState.update { it.copy(randomPersonError = error) }
        }
    }

    private fun observeCounter() = counterUseCase.execute(CounterUseCase.Args(interval = 200.milliseconds)) {
        onNext { count ->
            logger.d { "count: $count" }
            componentState.update { it.copy(counter = count) }

            if (count == COUNTER_ALERT) {
                logger.d { "counter reached $COUNTER_ALERT" }
                sendEvent(FirstUiEvent.Notify(count))
            }
        }
    }
}
