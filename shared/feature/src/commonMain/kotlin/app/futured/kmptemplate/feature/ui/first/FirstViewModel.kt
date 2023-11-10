package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import app.futured.kmptemplate.util.ext.update
import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.milliseconds

internal class FirstViewModel(
    arg: String,
    private val navigator: HomeNavigator
) : SharedViewModel<FirstViewState, FirstEvent, FirstUiEvent>(),
    FirstScreen.Actions {
    override val viewState: MutableValue<FirstViewState> = MutableValue(FirstViewState())

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

    override fun onBack() {
        navigator.pop()
    }

    override fun onNext() {
        navigator.push(HomeDestination.Second)
    }
}
