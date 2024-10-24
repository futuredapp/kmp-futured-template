package app.futured.kmptemplate.feature_v3.navigation.home

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext

object HomeNavHostFactory {
    fun create(componentContext: AppComponentContext): HomeNavHost = HomeNavHostComponent(componentContext)
}
