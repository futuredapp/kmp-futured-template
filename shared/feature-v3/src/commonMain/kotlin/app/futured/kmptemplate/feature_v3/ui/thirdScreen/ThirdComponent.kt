package app.futured.kmptemplate.feature_v3.ui.thirdScreen

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.format
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class ThirdComponent(
    componentContext: AppComponentContext,
    @InjectedParam args: ThirdScreenArgs,
    @InjectedParam override val navigation: ThirdScreenNavigation,
) : ScreenComponent<ThirdViewState, Nothing, ThirdScreenNavigation>(
    componentContext = componentContext,
    defaultState = ThirdViewState(text = MR.strings.third_screen_text.format(args.id)),
), ThirdScreen {

    override val viewState: StateFlow<ThirdViewState> = componentState

    override val actions: ThirdScreen.Actions = object : ThirdScreen.Actions {
        override fun onBack() = navigation.pop()
    }
}
