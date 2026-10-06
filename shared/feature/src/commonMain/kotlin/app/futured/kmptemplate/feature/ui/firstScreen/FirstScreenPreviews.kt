package app.futured.kmptemplate.feature.ui.firstScreen

import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

object FirstScreenPreviews {
    fun viewState(
        counter: String = "42",
        randomPerson: String = "Obi-Wan Kenobi",
        createdAt: String = "Created at: 1977-05-25",
    ) = FirstViewState(
        counter = counter.desc(),
        randomPerson = randomPerson.desc(),
        createdAt = createdAt.desc(),
    )

    fun screen(viewState: FirstViewState = viewState()): FirstScreen = object : FirstScreen {
        override val viewState: StateFlow<FirstViewState> = MutableStateFlow(viewState)
        override val actions: FirstScreen.Actions = FirstScreen.Actions.noOpActions()
        override val events: Flow<FirstUiEvent> = emptyFlow()
    }
}
