package app.futured.kmptemplate.feature.ui.loginScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface LoginScreenNavigation : NavigationActions {
    fun LoginComponent.toSignedIn()
}
