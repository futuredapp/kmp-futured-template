package app.futured.kmptemplate.feature.ui.picker

import app.futured.kmptemplate.feature.ui.first.AppComponent
import app.futured.kmptemplate.util.arch.AppComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class PickerComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val navigation: PickerNavigationActions,
) : AppComponent<PickerViewState, Nothing>(componentContext, PickerViewState()),
    PickerScreen {

    override fun onStart() = Unit

    override val actions: PickerScreen.Actions = object : PickerScreen.Actions {
        override fun onItemResult(item: PickerViewState.Item) = navigation.onResult(PickerResult.Pick(item.id))
        override fun onDismissResult() = navigation.onResult(PickerResult.Dismissed)
    }
}
