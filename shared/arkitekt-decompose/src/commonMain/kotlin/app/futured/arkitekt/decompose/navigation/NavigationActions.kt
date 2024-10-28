package app.futured.arkitekt.decompose.navigation

interface NavigationActions

/**
 * TODO KDoc
 */
interface NavigationActionsProducer<NAV : NavigationActions> {
    val navigation: NAV
}
