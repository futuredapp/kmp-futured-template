package app.futured.kmptemplate.feature.ui.third

import app.futured.arkitekt.decompose.viewModel
import app.futured.kmptemplate.feature.AppComponentContext
import kotlinx.coroutines.flow.StateFlow

internal class ThirdComponent(
    componentContext: AppComponentContext,
) : AppComponentContext by componentContext, ThirdScreen {
    private val viewModel: ThirdViewModel by viewModel()
    override val viewState: StateFlow<ThirdViewState> = viewModel.viewState
    override val actions: ThirdScreen.Actions = viewModel
}
