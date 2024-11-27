package app.futured.kmptemplate.feature.ui.base

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.arkitekt.decompose.navigation.NavigationActionsProducer
import app.futured.arkitekt.decompose.presentation.BaseComponent

/**
 * TODO KDoc
 */
abstract class AppComponent<VS : Any, E : Any>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : BaseComponent<VS, E>(componentContext, defaultState), AppComponentContext by componentContext

/**
 * TODO KDoc
 */
abstract class ScreenComponent<VS : Any, E : Any, NAV : NavigationActions>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : AppComponent<VS, E>(componentContext, defaultState), NavigationActionsProducer<NAV>
