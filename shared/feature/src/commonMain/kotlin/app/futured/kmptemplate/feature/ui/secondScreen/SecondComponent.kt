package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.arkitekt.annotation.GenerateFactory
import app.futured.arkitekt.decompose.navigation.ResultFlow
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.picker.PickerArgs
import app.futured.kmptemplate.feature.ui.picker.PickerType
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class SecondComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: SecondScreenNavigation,
) : ScreenComponent<SecondViewState, Nothing, SecondScreenNavigation>(
    componentContext = componentContext,
    defaultState = SecondViewState,
),
    SecondScreen,
    SecondScreenNavigation by navigation,
    SecondScreen.Actions {

    override val viewState: StateFlow<SecondViewState> = componentState
    override val actions: SecondScreen.Actions = this
    private val pickerResults = ResultFlow<String>()

    override fun onBack() = pop()

    override fun onPickVeggie() = openPicker(PickerType.Vegetable, PickerArgs(results = pickerResults))

    override fun onPickFruit() = openPicker(PickerType.Fruit, PickerArgs(results = pickerResults))

    init {
        lifecycle.doOnCreate {
            collectResults()
        }
    }

    private fun collectResults() = launchWithHandler {
        pickerResults.collectLatest { result ->
            navigateToThird(result)
        }
    }
}
