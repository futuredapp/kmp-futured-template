package app.futured.kmptemplate.feature.ui.picker

import kotlinx.serialization.Serializable

@Serializable
data object PickerArgs // TODO result

@Serializable
sealed interface PickerType {
    data object Fruit : PickerType
    data object Vegetable : PickerType
}
