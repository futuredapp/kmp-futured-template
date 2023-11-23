package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.util.arch.ViewState
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

data class SecondViewState(
    val text: StringDesc = MR.strings.second_screen_text.desc(),
) : ViewState
