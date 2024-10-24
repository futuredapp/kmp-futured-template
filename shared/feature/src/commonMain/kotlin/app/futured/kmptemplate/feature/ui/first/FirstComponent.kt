package app.futured.kmptemplate.feature.ui.first

import app.futured.arkitekt.decompose.injection.viewModel
import app.futured.kmptemplate.feature.AppComponentContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal class FirstComponent(
    componentContext: AppComponentContext,
) : AppComponentContext by componentContext, FirstScreen {

    private val viewModel: FirstViewModel by viewModel()

    override val viewState: StateFlow<FirstViewState> = viewModel.viewState
    override val actions: FirstScreen.Actions = viewModel
    override val events: Flow<FirstUiEvent> = viewModel.uiEvents
}
