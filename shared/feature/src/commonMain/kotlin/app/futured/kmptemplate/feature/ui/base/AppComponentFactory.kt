package app.futured.kmptemplate.feature.ui.base

import app.futured.arkitekt.decompose.navigation.NavigationActions
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

/**
 * Factory object for creating application components using Koin.
 */
internal object AppComponentFactory : KoinComponent {

    /**
     * Injects a [ScreenComponent] instance with the specified context, navigation actions, and additional injection parameters.
     *
     * @param C The type of the screen component.
     * @param childContext The context of the component.
     * @param navigation The navigation actions for the component.
     * @param parameters Additional parameters for the component.
     * @return The created screen component.
     */
    inline fun <reified C : ScreenComponent<*, *, N>, reified N : NavigationActions> createScreenComponent(
        childContext: AppComponentContext,
        navigation: N,
        vararg parameters: Any?,
    ): C = get(
        qualifier = null,
        parameters = {
            parametersOf(childContext, navigation, *parameters)
        },
    )

    /**
     * Injects an [AppComponent] instance with the specified context and additional injection parameters.
     *
     * @param C The type of the application component.
     * @param childContext The context of the component.
     * @param parameters Additional parameters for the component.
     * @return The created application component.
     */
    inline fun <reified C : AppComponent<*, *>> createAppComponent(
        childContext: AppComponentContext,
        vararg parameters: Any?,
    ): C = get(
        qualifier = null,
        parameters = {
            parametersOf(childContext, *parameters)
        },
    )
}
