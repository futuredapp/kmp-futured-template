package app.futured.kmptemplate.feature.ui.interopCheck

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class InteropCheckComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: InteropCheckScreenNavigation,
) : ScreenComponent<InteropCheckViewState, InteropCheckEvent, InteropCheckScreenNavigation>(
    componentContext = componentContext,
    defaultState = InteropCheckViewState(),
),
    InteropCheckScreen,
    InteropCheckScreenNavigation by navigation,
    InteropCheckScreen.Actions {

    override val actions: InteropCheckScreen.Actions = this
    override val viewState: StateFlow<InteropCheckViewState> = componentState.asStateFlow()

    override fun onBack() = pop()
    override fun onLaunchWebBrowser() {
        sendUiEvent(InteropCheckEvent.LaunchWebBrowser(viewState.value.url))
    }
}
