package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.kmptemplate.network.rest.dto.Person
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.datetime.desc
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class FirstViewState(
    internal val counter: Long = 0L,
    internal val createdAt: Instant = Clock.System.now(),
    internal val randomPerson: Person? = null,
    internal val randomPersonError: Throwable? = null,
) {
    companion object {
        fun mock() = FirstViewState()
    }
    val counterText: StringDesc
        get() = MR.strings.first_screen_counter.format(counter)

    val createdAtText: StringDesc
        get() = MR.strings.first_screen_created_at.format(createdAt.desc("Hms"))

    val randomPersonText: StringDesc?
        get() = when {
            randomPersonError != null -> MR.strings.first_screen_random_person.format("Failed to fetch")
            randomPerson != null -> MR.strings.first_screen_random_person.format(randomPerson.name.orEmpty())
            else -> null
        }
}
