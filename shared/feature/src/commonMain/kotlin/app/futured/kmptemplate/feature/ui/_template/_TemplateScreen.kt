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

internal interface TEMPLATEScreenNavigation : NavigationActions {
    fun TEMPLATEComponent.pop()
}

data object TEMPLATEViewState

@Factory
internal class TEMPLATEComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: TEMPLATEScreenNavigation,
) : ScreenComponent<TEMPLATEViewState, Nothing, TEMPLATEScreenNavigation>(
    componentContext = componentContext,
    defaultState = TEMPLATEViewState,
), TEMPLATEScreen, TEMPLATEScreenNavigation by navigation,
    TEMPLATEScreen.Actions {

    override val actions: TEMPLATEScreen.Actions = this
    override val viewState: StateFlow<TEMPLATEViewState> = componentState.asStateFlow()

    override fun onBack() = pop()
}
