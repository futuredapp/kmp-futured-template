package app.futured.kmptemplate.feature.ui.first

import com.rudolfhladik.annotation.Component
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Component(argType = String::class)
interface FirstScreen {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions
    val events: Flow<FirstEvent>

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
