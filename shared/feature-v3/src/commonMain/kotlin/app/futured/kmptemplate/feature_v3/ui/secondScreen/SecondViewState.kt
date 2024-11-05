package app.futured.kmptemplate.feature_v3.ui.secondScreen

import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

data class SecondViewState(
    val text: StringDesc = MR.strings.second_screen_text.desc(),
)
