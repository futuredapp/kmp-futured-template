package app.futured.kmptemplate.feature.ui.picker

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal data class PickerNavigation(val dismiss: (item: String?) -> Unit) : NavigationActions
