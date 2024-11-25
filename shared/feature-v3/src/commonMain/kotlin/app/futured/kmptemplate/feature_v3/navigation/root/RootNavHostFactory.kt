package app.futured.kmptemplate.feature_v3.navigation.root

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentFactory
import org.koin.core.component.KoinComponent

object RootNavHostFactory : KoinComponent {
    fun create(componentContext: AppComponentContext): RootNavHost =
        AppComponentFactory.createComponent<RootNavHostComponent>(componentContext)
}
