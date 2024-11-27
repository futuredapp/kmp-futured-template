package app.futured.kmptemplate.feature.ui._template

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

/**
 * A copy-paste template for creating new screen.
 * All you need to do is to copy contents of this file into your destination package and replace "TEMPLATE" with name of the screen.
 *
 * Hint: Ctrl+G
 */
interface TEMPLATEScreen {
    val viewState: StateFlow<TEMPLATEViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}

internal data class TEMPLATEScreenNavigation(
    val pop: () -> Unit,
) : NavigationActions

data object TEMPLATEViewState

@Factory
internal class TEMPLATEComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: TEMPLATEScreenNavigation,
) : ScreenComponent<TEMPLATEViewState, Nothing, TEMPLATEScreenNavigation>(componentContext, TEMPLATEViewState), TEMPLATEScreen {

    override val actions: TEMPLATEScreen.Actions = object : TEMPLATEScreen.Actions {
        override fun onBack() = navigation.pop()
    }

    override val viewState: StateFlow<TEMPLATEViewState> = componentState.whenStarted {
        // onStart
    }
}
