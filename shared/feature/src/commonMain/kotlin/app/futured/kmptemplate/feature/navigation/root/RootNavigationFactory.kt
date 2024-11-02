package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.AppComponentContext

object RootNavigationFactory {
    fun create(componentContext: AppComponentContext): RootNavigation = RootNavigationComponent(componentContext)
}
