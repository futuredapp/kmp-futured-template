package app.futured.arkitekt.decompose.navigation

interface NavigationActions

interface NavigationActionsProducer<NAV : NavigationActions> {
    val navigation: NAV
}
