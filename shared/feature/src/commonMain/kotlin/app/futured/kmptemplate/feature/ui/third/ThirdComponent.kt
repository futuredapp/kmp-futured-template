package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.arch.viewModel
import kotlinx.coroutines.flow.StateFlow

internal class ThirdComponent(
    componentContext: AppComponentContext,
) : AppComponentContext by componentContext, ThirdScreen {
    private val viewModel: ThirdViewModel by viewModel()
    override val viewState: StateFlow<ThirdViewState> = viewModel.viewState
    override val actions: ThirdScreen.Actions = viewModel
}
