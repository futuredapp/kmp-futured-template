package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface SecondScreenNavigation : NavigationActions {
    fun SecondComponent.pop()
}
