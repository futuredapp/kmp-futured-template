package app.futured.kmptemplate.feature_v3.ui.firstScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

interface FirstScreenNavigationActions : NavigationActions {
    fun pop()
    fun navigateToSecond()
}
