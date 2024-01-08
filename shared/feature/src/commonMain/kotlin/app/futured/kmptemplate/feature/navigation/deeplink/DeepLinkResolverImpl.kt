package app.futured.kmptemplate.feature.navigation.deeplink

import org.koin.core.annotation.Single

@Single
internal class DeepLinkResolverImpl : DeepLinkResolver {

    companion object {
        private val DeepLinkRegex = "kmptemplate://(secret|third)(\\?arg=(.+))*".toRegex()
    }

    override fun resolve(uri: String): DeepLinkDestination? {
        if (uri.matches(DeepLinkRegex)) {
            val matchResult = DeepLinkRegex.find(uri) ?: return null
            val screenName = matchResult.groupValues[1]
            val arg = matchResult.groupValues[3].takeIf { it.isNotBlank() }
            return when (screenName) {
                "third" -> DeepLinkDestination.ThirdScreen
                "secret" -> DeepLinkDestination.SecretScreen(argument = arg)
                else -> null
            }
        }
        return null
    }
}
