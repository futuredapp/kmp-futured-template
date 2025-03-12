package app.futured.kmptemplate.feature.ui.thirdScreen

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.format
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class ThirdComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam args: ThirdScreenArgs,
    @InjectedParam override val navigation: ThirdScreenNavigation,
) : ScreenComponent<ThirdViewState, Nothing, ThirdScreenNavigation>(
    componentContext = componentContext,
    defaultState = ThirdViewState(text = MR.strings.third_screen_text.format(args.id)),
), ThirdScreen, ThirdScreenNavigation by navigation, ThirdScreen.Actions {

    override val viewState: StateFlow<ThirdViewState> = componentState

    override val actions: ThirdScreen.Actions = this

    override fun onBack() = pop()
}
