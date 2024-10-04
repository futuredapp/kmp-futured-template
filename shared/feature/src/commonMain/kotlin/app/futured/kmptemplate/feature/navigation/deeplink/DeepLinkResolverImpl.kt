package app.futured.kmptemplate.feature.navigation.deeplink

import io.ktor.http.Url
import io.ktor.util.toMap
import org.koin.core.annotation.Single

@Single
internal class DeepLinkResolverImpl : DeepLinkResolver {

    companion object {
        private val LoginRegex = "kmptemplate://login".toRegex()
        private val TabARegex = "kmptemplate://a".toRegex()
        private val TabBRegex = "kmptemplate://b".toRegex()
        private val TabCRegex = "kmptemplate://c".toRegex()
        private val TabBThirdScreenRegex = "kmptemplate://b/third".toRegex()
        private val TabBSecretScreenRegex = "kmptemplate://b/secret(\\?arg=(.+))*".toRegex()
    }

    override fun resolve(uri: String): DeepLinkDestination? {
        return when {
            LoginRegex.matches(uri) -> DeepLinkDestination.Login
            TabARegex.matches(uri) -> DeepLinkDestination.TabA
            TabBRegex.matches(uri) -> DeepLinkDestination.TabB
            TabCRegex.matches(uri) -> DeepLinkDestination.TabC
            TabBThirdScreenRegex.matches(uri) -> DeepLinkDestination.ThirdScreen
            TabBSecretScreenRegex.matches(uri) -> {
                val params = Url(uri).parameters.toMap()
                val argument = params["arg"]?.first()
                DeepLinkDestination.SecretScreen(argument)
            }

            else -> null
        }
    }
}
