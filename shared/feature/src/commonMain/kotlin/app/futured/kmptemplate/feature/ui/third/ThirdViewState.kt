package app.futured.kmptemplate.feature.ui.third

import app.futured.arkitekt.decompose.presentation.ViewState
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

data class ThirdViewState(
    val text: StringDesc = MR.strings.third_screen_text.desc(),
) : ViewState
