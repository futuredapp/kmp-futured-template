package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.feature.data.model.ui.args.SecondScreenArgs
import com.rudolfhladik.annotation.Component
import kotlinx.coroutines.flow.StateFlow

@Component(argType = SecondScreenArgs::class)
interface SecondScreen {
    val viewState: StateFlow<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
