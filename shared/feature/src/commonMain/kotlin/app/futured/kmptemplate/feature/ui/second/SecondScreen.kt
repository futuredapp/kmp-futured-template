package app.futured.kmptemplate.feature.ui.second

import com.rudolfhladik.annotation.Component
import kotlinx.coroutines.flow.StateFlow

@Component
interface SecondScreen {
    val viewState: StateFlow<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
