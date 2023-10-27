package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.SharedViewModel
import app.futured.kmptemplate.util.ext.update
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.milliseconds

internal class FirstViewModel :
    SharedViewModel<FirstViewState, FirstEvent, Nothing>(),
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
                delay(200.milliseconds)
            }
        }
    }
}
