package app.futured.kmptemplate.feature.navigation.signedin.tab

import app.futured.kmptemplate.feature.ui.picker.PickerScreen
import app.futured.kmptemplate.util.arch.NavEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class SignedInSlotDestination {

    @Serializable
    data object Picker : SignedInSlotDestination()
}

sealed class SignedInSlotNavEntry : NavEntry {
    data class Picker(val screen: PickerScreen) : SignedInSlotNavEntry()
}
