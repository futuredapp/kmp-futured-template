package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.util.arch.AppComponentContext

object RootNavigationFactory {
    fun create(componentContext: AppComponentContext): RootNavigation = RootNavigationComponent(componentContext)
}
