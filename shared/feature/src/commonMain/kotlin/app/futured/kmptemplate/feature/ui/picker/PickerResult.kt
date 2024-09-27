package app.futured.kmptemplate.feature.ui.picker

import kotlinx.serialization.Serializable

@Serializable
sealed class PickerResult {
    @Serializable
    data class Pick(val id: String) : PickerResult()

    @Serializable
    data object Dismissed : PickerResult()
}
