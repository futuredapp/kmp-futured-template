package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class FirstScreenNavigation(
    val toSecond: () -> Unit,
) : NavigationActions
