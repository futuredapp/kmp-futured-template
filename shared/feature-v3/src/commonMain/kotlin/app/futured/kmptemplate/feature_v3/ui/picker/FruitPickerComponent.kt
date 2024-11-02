package app.futured.kmptemplate.feature_v3.ui.picker

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import kotlin.time.Duration.Companion.seconds

@Factory
internal class FruitPickerComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: PickerNavigation,
) : ScreenComponent<PickerState, Nothing, PickerNavigation>(componentContext, PickerState()),
    Picker {

    override val actions: Picker.Actions = object : Picker.Actions {
        override fun onPick(item: String) = navigation.dismiss(item)
    }

    override fun onStart() {
        launchWithHandler {
            updateState { copy(isLoading = true) }
            delay(2.seconds)
            updateState {
                copy(
                    isLoading = false, items = persistentListOf(
                        "Apples",
                        "Bananas",
                        "Oranges"
                    )
                )
            }
        }
    }
}
