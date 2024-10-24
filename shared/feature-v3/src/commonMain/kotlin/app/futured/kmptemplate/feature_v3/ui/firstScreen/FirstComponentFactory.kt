package app.futured.kmptemplate.feature_v3.ui.firstScreen

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal object FirstComponentFactory : KoinComponent {

    fun create(componentContext: AppComponentContext, navigation: FirstScreenNavigationActions): FirstComponent =
        FirstComponent(componentContext, navigation, get(), get())
}

