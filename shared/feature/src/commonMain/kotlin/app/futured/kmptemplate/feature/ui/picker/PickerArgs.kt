package app.futured.kmptemplate.feature.ui.picker

import kotlinx.serialization.Serializable

@Serializable
data object PickerArgs // TODO result

enum class PickerType {
    Fruit,
    Vegetable
}
