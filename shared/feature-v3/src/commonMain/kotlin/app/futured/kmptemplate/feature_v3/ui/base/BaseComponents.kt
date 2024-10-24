package app.futured.kmptemplate.feature_v3.ui.base

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.arkitekt.decompose.navigation.NavigationActionsProducer
import app.futured.arkitekt.decompose.presentation.BaseComponent
import app.futured.arkitekt.decompose.presentation.UiEvent
import app.futured.arkitekt.decompose.presentation.ViewState

/**
 * Base screen component. Each screen in application implements [AppComponentContext] and is [NavigationActionsProducer], which means
 * it has navigation functionality.
 */
abstract class AppComponent<VS : ViewState, E : UiEvent<VS>>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : BaseComponent<VS, E>(componentContext, defaultState), AppComponentContext by componentContext

/**
 * Base component which implements [AppComponentContext] screen component.
 */
abstract class ScreenComponent<VS : ViewState, E : UiEvent<VS>, NAV : NavigationActions>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : AppComponent<VS, E>(componentContext, defaultState), NavigationActionsProducer<NAV>
