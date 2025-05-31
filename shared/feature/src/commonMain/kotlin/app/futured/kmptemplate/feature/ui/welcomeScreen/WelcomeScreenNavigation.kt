package app.futured.kmptemplate.feature.ui.welcomeScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface WelcomeScreenNavigation : NavigationActions {
    fun WelcomeComponent.navigateToHome()
}
