package app.futured.kmptemplate.feature.ui.picker

import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.arch.NavigationResultFlow
import app.futured.kmptemplate.util.arch.viewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.parameter.parametersOf

internal class PickerComponent(
    componentContext: AppComponentContext,
    navigationResults: NavigationResultFlow<PickerResult>,
) : AppComponentContext by componentContext,
    PickerScreen {

    private val viewModel: PickerViewModel by viewModel(parameters = {
        parametersOf(
            navigationResults
        )
    })
    override val viewState: StateFlow<PickerViewState> = viewModel.viewState
    override val actions: PickerScreen.Actions = viewModel
}
