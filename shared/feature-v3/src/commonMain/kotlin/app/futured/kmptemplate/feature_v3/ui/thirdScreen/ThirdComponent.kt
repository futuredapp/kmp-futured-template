package app.futured.kmptemplate.feature_v3.ui.thirdScreen

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import dev.icerock.moko.resources.desc.desc
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class ThirdComponent(
    componentContext: AppComponentContext,
    @InjectedParam args: ThirdScreenArgs,
    @InjectedParam override val navigation: ThirdScreenNavigation,
) : ScreenComponent<ThirdViewState, Nothing, ThirdScreenNavigation>(
    componentContext = componentContext,
    defaultState = ThirdViewState(
        id = "Screen argument: ${args.id}".desc()
    ),
), ThirdScreen {

    override fun onStart() = Unit

    override val actions: ThirdScreen.Actions = object : ThirdScreen.Actions {
        override fun onBack() = navigation.pop()
    }
}
