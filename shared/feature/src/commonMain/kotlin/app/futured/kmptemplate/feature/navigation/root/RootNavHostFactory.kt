package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.AppComponentFactory
import org.koin.core.component.KoinComponent

object RootNavHostFactory : KoinComponent {
    fun create(
        componentContext: AppComponentContext,
    ): RootNavHost = AppComponentFactory.createAppComponent<RootNavHostComponent>(componentContext)
}
