package app.futured.kmptemplate.feature.ui.picker

import app.futured.arkitekt.decompose.navigation.ResultKey
import kotlinx.serialization.Serializable

@Serializable
data class PickerArgs(val resultKey: ResultKey<String>)

enum class PickerType {
    Fruit,
    Vegetable,
}
