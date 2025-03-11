package app.futured.kmptemplate.feature.ui.profileScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface ProfileScreenNavigation : NavigationActions {
    fun ProfileScreen.navigateToLogin()
    fun ProfileScreen.navigateToThird(id: String)
}
