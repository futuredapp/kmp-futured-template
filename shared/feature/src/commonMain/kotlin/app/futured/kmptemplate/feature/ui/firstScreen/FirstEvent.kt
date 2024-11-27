package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.presentation.UiEvent
import dev.icerock.moko.resources.desc.StringDesc

sealed class FirstUiEvent : UiEvent {
    data class ShowToast(val text: StringDesc) : FirstUiEvent()
}
