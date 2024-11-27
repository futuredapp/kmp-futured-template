package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.AppComponentFactory
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.picker.FruitPickerComponent
import app.futured.kmptemplate.feature.ui.picker.Picker
import app.futured.kmptemplate.feature.ui.picker.PickerNavigation
import app.futured.kmptemplate.feature.ui.picker.VegetablePickerComponent
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class SecondComponent(
    componentContext: AppComponentContext,
    @InjectedParam override val navigation: SecondScreenNavigation,
) : ScreenComponent<SecondViewState, Nothing, SecondScreenNavigation>(
    componentContext = componentContext,
    defaultState = SecondViewState,
), SecondScreen {

    override val viewState: StateFlow<SecondViewState> = componentState

    private val pickerNavigator = SlotNavigation<SecondScreen.PickerType>()
    private val pickerNavigation = PickerNavigation(
        dismiss = { result ->
            pickerNavigator.dismiss()
            if (result != null) {
                navigation.toThird(result)
            }
        },
    )

    override val picker: StateFlow<ChildSlot<SecondScreen.PickerType, Picker>> = childSlot(
        source = pickerNavigator,
        serializer = SecondScreen.PickerType.serializer(),
        childFactory = { type, childContext ->
            when (type) {
                SecondScreen.PickerType.Fruit -> AppComponentFactory.createComponent<FruitPickerComponent>(
                    childContext,
                    pickerNavigation,
                )

                SecondScreen.PickerType.Vegetable -> AppComponentFactory.createComponent<VegetablePickerComponent>(
                    childContext,
                    pickerNavigation,
                )
            }
        },
    ).asStateFlow(componentCoroutineScope)

    override val actions: SecondScreen.Actions = object : SecondScreen.Actions {
        override fun onBack() = navigation.pop()
        override fun onPickVeggie() = pickerNavigator.activate(SecondScreen.PickerType.Vegetable)
        override fun onPickFruit() = pickerNavigator.activate(SecondScreen.PickerType.Fruit)
        override fun onPickerDismissed() = pickerNavigator.dismiss()
    }
}
