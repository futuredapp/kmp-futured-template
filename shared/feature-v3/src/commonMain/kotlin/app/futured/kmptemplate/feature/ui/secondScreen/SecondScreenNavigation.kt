package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class SecondScreenNavigation(
    val pop: () -> Unit,
    val toThird: (id: String) -> Unit,
) : NavigationActions
