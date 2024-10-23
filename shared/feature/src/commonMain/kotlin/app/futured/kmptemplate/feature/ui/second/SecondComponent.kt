package app.futured.kmptemplate.feature.ui.second

import app.futured.arkitekt.decompose.AppComponentContext
import app.futured.arkitekt.decompose.viewModel
import kotlinx.coroutines.flow.StateFlow

internal class SecondComponent(
    componentContext: AppComponentContext,
) : AppComponentContext by componentContext, SecondScreen {

    private val viewModel: SecondViewModel by viewModel()
    override val viewState: StateFlow<SecondViewState> = viewModel.viewState
    override val actions: SecondScreen.Actions = viewModel
}
