package app.futured.kmptemplate.feature.ui.picker

import app.futured.arkitekt.decompose.navigation.ResultFlow
import kotlinx.serialization.Serializable

@Serializable
data class PickerArgs(val results: ResultFlow<String>)

enum class PickerType {
    Fruit,
    Vegetable,
}
