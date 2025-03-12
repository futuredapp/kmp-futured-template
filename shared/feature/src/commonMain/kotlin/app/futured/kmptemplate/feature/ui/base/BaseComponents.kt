package app.futured.kmptemplate.feature.ui.base

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.arkitekt.decompose.navigation.NavigationActionsProducer
import app.futured.arkitekt.decompose.presentation.BaseComponent

/**
 * Base class for application components - usually nav host components that are not screens and do not need to implement
 * navigation actions.
 *
 * @param VS The type of the component state.
 * @param E The type of the event.
 * @param componentContext The context of the component.
 * @param defaultState The default state of the component.
 */
abstract class AppComponent<VS : Any, E : Any>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : BaseComponent<VS, E>(componentContext, defaultState), AppComponentContext by componentContext

/**
 * Base class for screen components with navigation actions.
 *
 * @param VS The type of the view state.
 * @param E The type of the event.
 * @param NAV The type of the navigation actions.
 * @param componentContext The context of the component.
 * @param defaultState The default state of the component.
 */
abstract class ScreenComponent<VS : Any, E : Any, NAV : NavigationActions>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : AppComponent<VS, E>(componentContext, defaultState), NavigationActionsProducer<NAV>
