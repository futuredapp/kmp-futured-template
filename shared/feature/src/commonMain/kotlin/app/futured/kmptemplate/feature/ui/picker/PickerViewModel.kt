package app.futured.kmptemplate.feature.ui.picker

import app.futured.kmptemplate.util.arch.NavigationResultFlow
import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
internal class PickerViewModel(
    override val navigationResults: NavigationResultFlow<Any>,
) : SharedViewModel<PickerViewState, Nothing>(),
    PickerScreen.Actions {

    override val viewState: MutableStateFlow<PickerViewState> = MutableStateFlow(
        PickerViewState(),
    )

    override fun onItemResult(item: PickerViewState.Item) {
        setNavigationResult(PickerResult.Pick(item.id))
    }

    override fun onDismissResult() {
        setNavigationResult(PickerResult.Dismissed)
    }
}
