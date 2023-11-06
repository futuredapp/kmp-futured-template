package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

internal class SecondComponent(
    componentContext: ComponentContext,
    override val output: (SecondEvent) -> Unit,
) : ViewModelComponent<SecondViewModel, SecondEvent>(componentContext), SecondScreen {
    override val viewModel: SecondViewModel by viewModel()
    override val viewState: StateFlow<SecondViewState> = viewModel.viewState
    override val actions: SecondScreen.Actions = viewModel
}
