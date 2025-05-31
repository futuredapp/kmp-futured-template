package app.futured.kmptemplate.feature.ui.thirdScreen

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ThirdScreen {

    val viewState: StateFlow<ThirdViewState>
    val actions: Actions
    val events: Flow<ThirdUIEvent>


    interface Actions {
        fun onBack()
        fun onShare()
        fun onShareAvatarLoadingStarted()
        fun onShareAvatarLoadingFinished()
    }
}
