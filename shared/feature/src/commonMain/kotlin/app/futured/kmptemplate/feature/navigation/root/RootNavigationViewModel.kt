package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
internal class RootNavigationViewModel :
    SharedViewModel<RootNavigationViewState, Nothing>() {
    override val viewState: MutableStateFlow<RootNavigationViewState> = MutableStateFlow(RootNavigationViewState())
}
