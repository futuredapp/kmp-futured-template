package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.presentation.UiEvent
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

sealed interface FirstUiEvent : UiEvent {

    data class Notify(internal val count: Long) : FirstUiEvent {
        val text: StringDesc
            get() = MR.strings.first_screen_counter_alert.format(count)
    }
}
