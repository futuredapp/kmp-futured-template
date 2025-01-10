package app.futured.kmptemplate.feature.ui.thirdScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class ThirdScreenNavigation(
    val pop: () -> Unit,
) : NavigationActions
