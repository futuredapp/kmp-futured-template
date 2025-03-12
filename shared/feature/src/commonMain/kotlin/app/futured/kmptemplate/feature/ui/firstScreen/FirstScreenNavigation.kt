package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface FirstScreenNavigation : NavigationActions {
    fun FirstComponent.navigateToSecond()
}
