package app.futured.kmptemplate.feature_v3.ui.picker

import app.futured.arkitekt.decompose.presentation.ViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PickerState(
    val isLoading: Boolean = false,
    val items: ImmutableList<String> = persistentListOf(),
) : ViewState
