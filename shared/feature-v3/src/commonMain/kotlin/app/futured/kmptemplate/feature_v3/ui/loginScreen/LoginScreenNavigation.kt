package app.futured.kmptemplate.feature_v3.ui.loginScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal data class LoginScreenNavigation(
    val toSignedIn: () -> Unit,
) : NavigationActions
