package app.futured.kmptemplate.feature.ui.interopCheck

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckComponent

internal interface InteropCheckScreenNavigation : NavigationActions {
    fun InteropCheckComponent.pop()
}
