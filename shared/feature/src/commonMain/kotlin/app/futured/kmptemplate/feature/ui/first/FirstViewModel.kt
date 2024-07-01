package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigator
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.util.arch.SharedViewModel
import app.futured.kmptemplate.util.ext.update
import co.touchlab.kermit.Logger
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Factory

@Factory
internal class FirstViewModel(
    private val tabBNavigator: TabBNavigator,
) : SharedViewModel<FirstViewState, FirstUiEvent>(),
    FirstScreen.Actions {

    override val viewState: MutableStateFlow<FirstViewState> = MutableStateFlow(FirstViewState())

    init {
        launchWithHandler {
            var counter = 0
            while (isActive) {
                update(viewState) {
                    copy(
                        text = StringDesc.ResourceFormatted(stringRes = MR.strings.first_screen_text, 1, counter++),
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

    override fun onBack() = tabBNavigator.pop()

    override fun onNext() = tabBNavigator.navigateToSecond()
}
