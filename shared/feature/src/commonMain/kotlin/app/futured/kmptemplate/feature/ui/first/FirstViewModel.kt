package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeStackNavigator
import app.futured.kmptemplate.feature.navigation.root.RootDestination
import app.futured.kmptemplate.feature.navigation.root.RootSlotNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import app.futured.kmptemplate.util.ext.update
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.stack.push
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Factory
import kotlin.time.Duration.Companion.milliseconds

@Factory
internal class FirstViewModel(
    private val homeNavigator: HomeStackNavigator,
    private val rootNavigator: RootSlotNavigator,
) : SharedViewModel<FirstViewState, FirstEvent, FirstUiEvent>(),
    FirstScreen.Actions {
    override val viewState: MutableStateFlow<FirstViewState> = MutableStateFlow(FirstViewState())

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
        rootNavigator.activate(RootDestination.Login)
    }

    override fun onNext() {
        homeNavigator.push(HomeDestination.Second)
    }
}
