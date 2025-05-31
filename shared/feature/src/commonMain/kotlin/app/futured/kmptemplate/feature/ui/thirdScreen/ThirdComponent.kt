package app.futured.kmptemplate.feature.ui.thirdScreen

import app.futured.arkitekt.decompose.ext.update
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class ThirdComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: ThirdScreenNavigation,
    @InjectedParam args: ThirdScreenArgs,
) : ScreenComponent<ThirdViewState, ThirdUIEvent, ThirdScreenNavigation>(
    componentContext = componentContext,
    defaultState = ThirdViewState(args.avatar),
),
    ThirdScreen,
    ThirdScreenNavigation by navigation,
    ThirdScreen.Actions {

    override val viewState: StateFlow<ThirdViewState> = componentState

    override val actions: ThirdScreen.Actions = this

    override fun onBack() = pop()

    override fun onShare() {
        sendUiEvent(ThirdUIEvent.Share)
    }

    override fun onShareAvatarLoadingStarted() {
        update(componentState) {
            copy(shareAvatarImageLoading = true)
        }
    }

    override fun onShareAvatarLoadingFinished() {
        update(componentState) {
            copy(shareAvatarImageLoading = false)
        }
    }
}
