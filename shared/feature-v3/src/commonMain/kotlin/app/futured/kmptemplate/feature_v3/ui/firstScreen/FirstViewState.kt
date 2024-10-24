package app.futured.kmptemplate.feature_v3.ui.firstScreen

import app.futured.arkitekt.decompose.presentation.ViewState
import dev.icerock.moko.resources.desc.Raw
import dev.icerock.moko.resources.desc.StringDesc

data class FirstViewState(
    val text: StringDesc = StringDesc.Raw("Loading..."),
) : ViewState
