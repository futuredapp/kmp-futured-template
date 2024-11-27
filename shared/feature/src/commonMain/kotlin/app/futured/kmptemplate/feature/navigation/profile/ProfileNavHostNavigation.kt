package app.futured.kmptemplate.feature.navigation.profile

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class ProfileNavHostNavigation(
    val toLogin: () -> Unit,
) : NavigationActions
