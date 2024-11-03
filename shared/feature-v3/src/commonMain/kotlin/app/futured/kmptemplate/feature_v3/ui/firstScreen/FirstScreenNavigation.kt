package app.futured.kmptemplate.feature_v3.ui.firstScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class FirstScreenNavigation(
    val toSecond: () -> Unit,
) : NavigationActions
