package app.futured.kmptemplate.feature_v3.ui.base

import app.futured.arkitekt.decompose.navigation.NavigationActions
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

/**
 * TODO KDoc
 */
internal object AppComponentFactory : KoinComponent {

    /**
     * TODO KDoc
     */
    inline fun <reified C : ScreenComponent<*, *, *>> createComponent(
        childContext: AppComponentContext,
        navigation: NavigationActions, // TODO component-specific navigation class
        vararg parameters: Any?,
    ): C = get(
        qualifier = null,
        parameters = {
            parametersOf(childContext, navigation, *parameters)
        },
    )

    /**
     * TODO KDoc
     */
    inline fun <reified C : AppComponent<*, *>> createComponent(
        childContext: AppComponentContext,
        vararg parameters: Any?,
    ): C = get(
        qualifier = null,
        parameters = {
            parametersOf(childContext, *parameters)
        },
    )
}
