package app.futured.kmptemplate.feature.ui.interopCheck

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * A copy-paste InteropCheck for creating new screen.
 * All you need to do is to copy contents of this file into your destination package and replace "InteropCheck" with name of the screen.
 *
 * Hint: Ctrl+G
 */
interface InteropCheckScreen {
    val viewState: StateFlow<InteropCheckViewState>
    val actions: Actions
    val events: Flow<InteropCheckEvent>

    interface Actions {
        fun onBack()
        fun onLaunchWebBrowser()
    }
}

