package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.ViewState
import dev.icerock.moko.resources.desc.Raw
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

data class FirstViewState(
    val text: StringDesc = StringDesc.Raw("Loading..."),
    internal val pickerResult: String? = null
) : ViewState {

    val pickerResultText: StringDesc
        get() = "Picker result: $pickerResult".desc()
}
