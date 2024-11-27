package app.futured.kmptemplate.feature.navigation.deepLink

/**
 * Specifies all destinations reachable by deep links in application.
 */
sealed class DeepLinkDestination {

    data object HomeTab : DeepLinkDestination()

    data object ProfileTab : DeepLinkDestination()

    data object SecondScreen : DeepLinkDestination()

    data class ThirdScreen(val argument: String) : DeepLinkDestination()
}
