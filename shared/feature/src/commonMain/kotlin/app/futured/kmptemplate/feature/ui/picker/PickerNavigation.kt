package app.futured.kmptemplate.feature.ui.picker

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface PickerNavigation : NavigationActions {
    fun VegetablePickerComponent.dismiss()
    fun FruitPickerComponent.dismiss()
}
