package app.futured.kmptemplate.feature_v3.ui.base

import app.futured.arkitekt.decompose.navigation.NavigationActions
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal object ScreenComponentFactory : KoinComponent {

    inline fun <reified C : ScreenComponent<*, *, *>> createComponent(
        childContext: AppComponentContext,
        navigation: NavigationActions,
        vararg parameters: Any?,
    ): C = get(
        qualifier = null,
        parameters = {
            parametersOf(childContext, navigation, *parameters)
        },
    )
}
