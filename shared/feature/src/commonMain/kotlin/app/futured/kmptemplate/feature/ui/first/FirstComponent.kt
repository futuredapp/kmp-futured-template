package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal class FirstComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<FirstViewModel>(componentContext), FirstScreen {
    override val viewModel: FirstViewModel by viewModel()
    override val viewState: StateFlow<FirstViewState> = viewModel.viewState
    override val actions: FirstScreen.Actions = viewModel
    override val events: Flow<FirstEvent> = viewModel.uiEvents
}
