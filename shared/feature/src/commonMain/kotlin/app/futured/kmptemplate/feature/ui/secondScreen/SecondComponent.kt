package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.picker.PickerArgs
import app.futured.kmptemplate.feature.ui.picker.PickerType
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import kotlinx.coroutines.flow.StateFlow
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

    override fun onBack() = pop()

    override fun onPickVeggie() = openPicker(PickerType.Vegetable, PickerArgs)

    override fun onPickFruit() = openPicker(PickerType.Fruit, PickerArgs)
}
