package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

internal class ThirdComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<ThirdViewModel>(componentContext), ThirdScreen {
    override val viewModel: ThirdViewModel by viewModel()
    override val viewState: StateFlow<ThirdViewState> = viewModel.viewState
    override val actions: ThirdScreen.Actions = viewModel
}
