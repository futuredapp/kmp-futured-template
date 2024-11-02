package app.futured.kmptemplate.feature.ui.first

import app.futured.arkitekt.decompose.presentation.ViewState
import dev.icerock.moko.resources.desc.Raw
import dev.icerock.moko.resources.desc.StringDesc

data class FirstViewState(
    val text: StringDesc = StringDesc.Raw("Loading..."),
) : ViewState
