package app.futured.kmptemplate.feature.ui.formScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature.ui.formScreen.FormComponent

internal interface FormScreenNavigation : NavigationActions {
    fun FormComponent.pop()
    fun FormComponent.navigateToInteropCheck()
}
