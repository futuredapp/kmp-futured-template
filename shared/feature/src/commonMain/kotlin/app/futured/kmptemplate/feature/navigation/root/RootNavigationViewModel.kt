package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class RootNavigationViewModel : SharedViewModel<RootNavigationViewState, RootNavigationEvent, Nothing>() {
    override val viewState: MutableStateFlow<RootNavigationViewState> = MutableStateFlow(RootNavigationViewState())
}
