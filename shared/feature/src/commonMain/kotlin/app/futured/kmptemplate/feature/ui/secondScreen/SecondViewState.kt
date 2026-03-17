package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.datetime.desc
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format
import kotlin.time.Clock
import kotlin.time.Instant

data class SecondViewState(
    internal val createdAt: Instant = Clock.System.now(),
) {
    val createdAtText: StringDesc
        get() = MR.strings.first_screen_created_at.format(createdAt.desc("Hms"))
}
