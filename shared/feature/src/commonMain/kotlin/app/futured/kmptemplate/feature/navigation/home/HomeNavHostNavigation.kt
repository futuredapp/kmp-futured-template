package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.firstScreen.FirstComponent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreenNavigation
import app.futured.kmptemplate.feature.ui.picker.FruitPickerComponent
import app.futured.kmptemplate.feature.ui.picker.PickerArgs
import app.futured.kmptemplate.feature.ui.picker.PickerNavigation
import app.futured.kmptemplate.feature.ui.picker.PickerType
import app.futured.kmptemplate.feature.ui.picker.VegetablePickerComponent
import app.futured.kmptemplate.feature.ui.secondScreen.SecondComponent
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreenNavigation
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponent
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenArgs
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenNavigation
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew

internal interface HomeNavHostNavigation :
    FirstScreenNavigation,
    SecondScreenNavigation,
    ThirdScreenNavigation,
    PickerNavigation {

    val stackNavigator: StackNavigation<HomeConfig>
    val sheetNavigator: SlotNavigation<HomeSheetConfig>
}

internal class HomeNavigator : HomeNavHostNavigation {
    override val stackNavigator = StackNavigation<HomeConfig>()
    override val sheetNavigator = SlotNavigation<HomeSheetConfig>()

    override fun FirstComponent.navigateToSecond() =
        stackNavigator.pushNew(HomeConfig.Second)

    override fun SecondComponent.pop() =
        stackNavigator.pop()

    override fun SecondComponent.openPicker(type: PickerType, args: PickerArgs) =
        sheetNavigator.activate(HomeSheetConfig.Picker(type, args))

    override fun SecondComponent.navigateToThird(id: String) =
        stackNavigator.pushNew(HomeConfig.Third(ThirdScreenArgs(id)))

    override fun ThirdComponent.pop() {
        stackNavigator.pop()
    }

    override fun VegetablePickerComponent.dismiss() = sheetNavigator.dismiss()

    override fun FruitPickerComponent.dismiss() = sheetNavigator.dismiss()
}
