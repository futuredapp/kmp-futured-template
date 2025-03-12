package app.futured.kmptemplate.feature.ui.profileScreen

import kotlinx.coroutines.flow.StateFlow

interface ProfileScreen {
    val viewState: StateFlow<ProfileViewState>
    val actions: Actions

    interface Actions {
        fun onLogout()
        fun onThird()
    }
}
