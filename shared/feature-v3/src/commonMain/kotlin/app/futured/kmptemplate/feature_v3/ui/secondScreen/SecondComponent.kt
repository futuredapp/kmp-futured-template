package app.futured.kmptemplate.feature_v3.ui.secondScreen

import app.futured.arkitekt.decompose.presentation.Stateless
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class SecondComponent(
    componentContext: AppComponentContext,
    @InjectedParam override val navigation: SecondScreenNavigationActions,
) : ScreenComponent<Stateless, Nothing, SecondScreenNavigationActions>(componentContext, Stateless), SecondScreen {

    override fun onStart() = Unit
}
