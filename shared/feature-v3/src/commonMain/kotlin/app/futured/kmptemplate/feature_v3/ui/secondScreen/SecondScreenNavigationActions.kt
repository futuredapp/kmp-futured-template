package app.futured.kmptemplate.feature_v3.ui.secondScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions

data class SecondScreenNavigation(
    val pop: () -> Unit,
) : NavigationActions
