package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature.model.ui.Avatar

internal interface FirstScreenNavigation : NavigationActions {
    fun FirstComponent.navigateToSecond()
    fun FirstComponent.navigateToThird(avatar: Avatar)
}
