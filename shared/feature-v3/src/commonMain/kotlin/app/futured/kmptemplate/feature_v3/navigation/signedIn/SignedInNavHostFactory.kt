package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object SignedInNavHostFactory : KoinComponent {
    fun create(componentContext: AppComponentContext): SignedInNavHost = SignedInNavHostComponent(
        componentContext = componentContext,
        deepLinkResolver = get(),
    )
}
