package app.futured.kmptemplate.feature.ui.profileScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal data class ProfileScreenNavigation(
    val toLogin: () -> Unit
) : NavigationActions
