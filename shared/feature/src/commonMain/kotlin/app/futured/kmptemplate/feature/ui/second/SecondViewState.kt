package app.futured.kmptemplate.feature.ui.second

import app.futured.arkitekt.decompose.presentation.ViewState
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

data class SecondViewState(
    val text: StringDesc = MR.strings.second_screen_text.desc(),
) : ViewState
