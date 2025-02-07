package app.futured.kmptemplate.feature.navigation.signedIn

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface SignedInNavHostNavigation: NavigationActions {
    fun SignedInNavHostComponent.toLogin()
}
