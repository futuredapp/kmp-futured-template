package app.futured.kmptemplate.feature.ui.picker

import app.futured.kmptemplate.util.arch.ViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PickerViewState(
    val items: ImmutableList<Item> = persistentListOf(
        Item("one"),
        Item("two"),
        Item("three"),
        Item("four"),
        Item("five"),
        Item("six"),
    ),
) : ViewState {
    data class Item(val id: String)
}
