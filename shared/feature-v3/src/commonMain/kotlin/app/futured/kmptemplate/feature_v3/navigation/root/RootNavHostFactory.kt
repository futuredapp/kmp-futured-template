package app.futured.kmptemplate.feature_v3.navigation.root

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext

object RootNavHostFactory {
    fun create(componentContext: AppComponentContext): RootNavHost =
        RootNavHostComponent(componentContext)
}
