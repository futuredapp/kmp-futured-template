package app.futured.kmptemplate.feature.ui.firstScreen

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

data class FirstViewState(val createdAt: StringDesc = "".desc(), val counter: StringDesc = "".desc(), val randomPerson: StringDesc? = null) {

    companion object {
        val mock = FirstViewState(
            counter = "999".desc(),
            randomPerson = "Obi-Wan Kenobi".desc(),
            createdAt = "Created at: 1977-05-25".desc(),
        )
    }
}
