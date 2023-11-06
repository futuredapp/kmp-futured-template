package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue

internal class ThirdComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<ThirdViewModel, ThirdEvent>(componentContext), ThirdScreen {
    override val output: (ThirdEvent) -> Unit = {}
    override val viewModel: ThirdViewModel by viewModel()
    override val viewState: MutableValue<ThirdViewState> = viewModel.viewState
    override val actions: ThirdScreen.Actions = viewModel
}
