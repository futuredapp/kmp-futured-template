package app.futured.kmptemplate.feature_v3.navigation.profile

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class ProfileNavHostNavigation(
    val toLogin: () -> Unit
) : NavigationActions
