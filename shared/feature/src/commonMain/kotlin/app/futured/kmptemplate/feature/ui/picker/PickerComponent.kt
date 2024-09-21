package app.futured.kmptemplate.feature.ui.picker

import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.arch.viewModel

internal class PickerComponent(
    componentContext: AppComponentContext,
) : AppComponentContext by componentContext {

    private val viewModel: PickerViewModel by viewModel()
}
