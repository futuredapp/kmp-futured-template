package app.futured.kmptemplate.feature_v3.ui.picker

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PickerState(
    val isLoading: Boolean = false,
    val items: ImmutableList<String> = persistentListOf(),
)

fun pickerStatePreviews() = persistentListOf(
    PickerState(
        isLoading = false,
        items = persistentListOf(
            "\uD83E\uDD55 Carrots",
            "\uD83E\uDED1 Peppers",
            "\uD83E\uDDC5 Onions",
        ),
    ),
    PickerState(isLoading = true, items = persistentListOf()),
)
