package app.futured.kmptemplate.feature.ui.first

import app.futured.arkitekt.decompose.ext.update
import app.futured.arkitekt.decompose.presentation.SharedViewModel
import app.futured.kmptemplate.feature.domain.CounterUseCase
import app.futured.kmptemplate.feature.domain.CounterUseCaseArgs
import app.futured.kmptemplate.feature.domain.SyncDataUseCase
import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigator
import app.futured.kmptemplate.resources.MR
import co.touchlab.kermit.Logger
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory
import kotlin.time.Duration.Companion.milliseconds

@Factory
internal class FirstViewModel(
    private val tabBNavigator: TabBNavigator,
    private val syncDataUseCase: SyncDataUseCase,
    private val counterUseCase: CounterUseCase,
) : SharedViewModel<FirstViewState, FirstUiEvent>(),
    FirstScreen.Actions {

    private val logger: Logger = Logger.withTag("FirstViewModel")

    override val viewState: MutableStateFlow<FirstViewState> = MutableStateFlow(FirstViewState())

    init {
        syncData()
        observeCounter()
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
        update(viewState) {
            copy(
                text = StringDesc.ResourceFormatted(stringRes = MR.strings.first_screen_text, 1, count),
            )
        }
    }

    override fun onBack() = tabBNavigator.pop()

    override fun onNext() = tabBNavigator.navigateToSecond()
}
