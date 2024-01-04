package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkDestination
import kotlinx.serialization.Serializable

@Serializable
data class HomeNavigationArgs(
    val deepLinkDestination: DeepLinkDestination? = null,
)
