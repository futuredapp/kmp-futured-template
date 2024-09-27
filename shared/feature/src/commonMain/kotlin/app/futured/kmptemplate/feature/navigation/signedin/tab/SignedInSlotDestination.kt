package app.futured.kmptemplate.feature.navigation.signedin.tab

import app.futured.kmptemplate.feature.ui.picker.PickerComponent
import app.futured.kmptemplate.feature.ui.picker.PickerResult
import app.futured.kmptemplate.feature.ui.picker.PickerScreen
import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.arch.Destination
import app.futured.kmptemplate.util.arch.NavEntry
import app.futured.kmptemplate.util.arch.NavigationResultFlow
import app.futured.kmptemplate.util.arch.NavigationResultFlowSerializer
import kotlinx.serialization.Serializable

@Serializable
sealed class SignedInSlotDestination : Destination<SignedInSlotNavEntry> {

    @Serializable
    data class Picker(
        @Serializable(with = NavigationResultFlowSerializer::class)
        val results: NavigationResultFlow<PickerResult>
    ) : SignedInSlotDestination() {
        override fun createComponent(componentContext: AppComponentContext): SignedInSlotNavEntry =
            SignedInSlotNavEntry.Picker(
                PickerComponent(componentContext, results)
            )
    }
}

sealed class SignedInSlotNavEntry : NavEntry {
    data class Picker(val screen: PickerScreen) : SignedInSlotNavEntry()
}
