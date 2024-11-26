package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class SignedInNavHostNavigation(
    val toLogin: () -> Unit,
) : NavigationActions
