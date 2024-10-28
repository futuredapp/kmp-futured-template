package app.futured.kmptemplate.feature_v3.ui.base

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.arkitekt.decompose.navigation.NavigationActionsProducer
import app.futured.arkitekt.decompose.presentation.BaseComponent
import app.futured.arkitekt.decompose.presentation.UiEvent
import app.futured.arkitekt.decompose.presentation.ViewState

abstract class AppComponent<VS : ViewState, E : UiEvent<VS>>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : BaseComponent<VS, E>(componentContext, defaultState), AppComponentContext by componentContext

abstract class ScreenComponent<VS : ViewState, E : UiEvent<VS>, NAV : NavigationActions>(
    componentContext: AppComponentContext,
    defaultState: VS,
) : AppComponent<VS, E>(componentContext, defaultState), NavigationActionsProducer<NAV>
