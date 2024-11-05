package app.futured.kmptemplate.feature_v3.navigation.root

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object RootNavHostFactory : KoinComponent {
    fun create(componentContext: AppComponentContext): RootNavHost = RootNavHostComponent(
        componentContext = componentContext,
        deepLinkResolver = get(),
    )
}
