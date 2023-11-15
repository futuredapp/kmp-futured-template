package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.SharedViewModel
import app.futured.kmptemplate.util.ext.update
import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Factory
import kotlin.time.Duration.Companion.milliseconds

@Factory
internal class FirstViewModel :
    SharedViewModel<FirstViewState, FirstEvent, FirstUiEvent>(),
    FirstScreen.Actions {
    override val viewState: MutableStateFlow<FirstViewState> = MutableStateFlow(FirstViewState())

    override fun onBack() = sendOutput(FirstEvent.NavigateBack)

    override fun onNext() = sendOutput(FirstEvent.NavigateNext)

    init {
        launchWithHandler {
            var counter = 0
            while (isActive) {
                update(viewState) {
                    copy(
                        text = "Screen number 1 x ${counter++}",
                    )
                }

                if (counter == 10) {
                    Logger.withTag("FirstViewmodel").d { "Counter reached 10" }
                    sendUiEvent(FirstUiEvent.ShowToast("Counter reached 10 🎉"))
                }

                delay(200.milliseconds)
            }
        }
    }
}
