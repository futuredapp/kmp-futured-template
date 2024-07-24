package app.futured.kmptemplate.feature.navigation.deeplink

import kotlinx.serialization.Serializable

/**
 * Specifies all destinations reachable by deep links in application.
 */
@Serializable
sealed class DeepLinkDestination {

    data object Login : DeepLinkDestination()

    data object TabA : DeepLinkDestination()

    data object TabB : DeepLinkDestination()

    data object TabC : DeepLinkDestination()

    @Serializable
    data class SecretScreen(val argument: String?) : DeepLinkDestination()

    @Serializable
    data object ThirdScreen : DeepLinkDestination()
}
