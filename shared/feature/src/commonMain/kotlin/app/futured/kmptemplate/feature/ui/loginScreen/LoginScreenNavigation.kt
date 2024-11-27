package app.futured.kmptemplate.feature.ui.loginScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal data class LoginScreenNavigation(
    val toSignedIn: () -> Unit,
) : NavigationActions
