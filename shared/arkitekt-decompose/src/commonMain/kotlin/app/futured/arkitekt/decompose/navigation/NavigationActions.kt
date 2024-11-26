package app.futured.arkitekt.decompose.navigation

/**
 * TODO This is useless
 */
interface NavigationActions

/**
 * TODO KDoc
 */
interface NavigationActionsProducer<NAV : NavigationActions> {
    val navigation: NAV
}
