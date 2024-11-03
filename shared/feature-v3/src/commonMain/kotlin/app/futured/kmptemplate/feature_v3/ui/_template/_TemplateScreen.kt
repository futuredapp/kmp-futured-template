package app.futured.kmptemplate.feature_v3.ui._template

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.arkitekt.decompose.presentation.ViewState
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

/*
TODO add TEMPLATE docs
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

data object TEMPLATEViewState : ViewState

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



