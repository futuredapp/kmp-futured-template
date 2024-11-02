package app.futured.kmptemplate.feature_v3.ui.profileScreen

import kotlinx.coroutines.flow.StateFlow

interface ProfileScreen {
    val viewState: StateFlow<ProfileViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
