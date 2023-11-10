package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import org.koin.core.parameter.parametersOf

internal class FirstComponent(
    componentContext: ComponentContext,
    arg: String,
) : ViewModelComponent<FirstViewModel, FirstEvent>(componentContext), FirstScreen {
    override val viewModel: FirstViewModel by viewModel { parametersOf(arg) }
    override val output: (FirstEvent) -> Unit = {}
    override val viewState: MutableValue<FirstViewState> = viewModel.viewState
    override val actions: FirstScreen.Actions = viewModel
}
