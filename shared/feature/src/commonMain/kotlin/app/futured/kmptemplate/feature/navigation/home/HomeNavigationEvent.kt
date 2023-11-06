package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.util.arch.OutputEvent

sealed class HomeNavigationEvent : OutputEvent<Nothing> {
    data object NavigateBack : HomeNavigationEvent()
}
