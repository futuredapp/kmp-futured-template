package app.futured.kmptemplate.feature_v3.ui.secondScreen

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class SecondComponent(
    componentContext: AppComponentContext,
    @InjectedParam override val navigation: SecondScreenNavigation,
) : ScreenComponent<SecondViewState, Nothing, SecondScreenNavigation>(
    componentContext = componentContext,
    defaultState = SecondViewState(),
), SecondScreen {

    override fun onStart() = Unit

    override val actions: SecondScreen.Actions = object : SecondScreen.Actions {
        override fun onBack() = navigation.pop()
        override fun onNext() = navigation.toThird("This ID should have been selected from picker component.")
    }
}
