package app.futured.kmptemplate.feature.ui.picker

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PickerState(
    val isLoading: Boolean = false,
    val items: ImmutableList<StringDesc> = persistentListOf(),
)

fun pickerStatePreviews() = persistentListOf(
    PickerState(
        isLoading = false,
        items = persistentListOf(
            "\uD83E\uDD55 Carrots".desc(),
            "\uD83E\uDED1 Peppers".desc(),
            "\uD83E\uDDC5 Onions".desc(),
        ),
    ),
    PickerState(isLoading = true, items = persistentListOf()),
)
