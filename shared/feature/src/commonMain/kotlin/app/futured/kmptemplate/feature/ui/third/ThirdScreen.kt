package app.futured.kmptemplate.feature.ui.third

import com.rudolfhladik.annotation.Component
import kotlinx.coroutines.flow.StateFlow

@Component
interface ThirdScreen {
    val viewState: StateFlow<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
