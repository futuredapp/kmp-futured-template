package app.futured.kmptemplate.feature.navigation.root

import app.futured.arkitekt.decompose.AppComponentContext

object RootNavigationFactory {
    fun create(componentContext: AppComponentContext): RootNavigation = RootNavigationComponent(componentContext)
}
