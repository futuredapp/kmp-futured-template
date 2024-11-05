package app.futured.kmptemplate.feature_v3.navigation.deepLink

import co.touchlab.kermit.Logger
import org.koin.core.annotation.Single

@Single
internal class DeepLinkResolverImpl : DeepLinkResolver {

    companion object {
        private val HomeRegex = "kmptemplate://home".toRegex()
        private val ProfileRegex = "kmptemplate://profile".toRegex()
        private val SecondScreenRegex = "kmptemplate://home/second".toRegex()
        private val ThirdScreenRegexWithQueryParam = "kmptemplate://home/third(\\?arg=(.+))*".toRegex()
        private val ThirdScreenRegexWithPathParam = "kmptemplate://home/third/(?<arg>.+)".toRegex()
    }

    private val logger = Logger.withTag("DeepLinkResolverImpl")

    override fun resolve(uri: String): DeepLinkDestination? {
        logger.i { "resolving deep link: $uri" }
        val destination = run {
            HomeRegex.toDeepLink(uri)?.let {
                return@run DeepLinkDestination.HomeTab
            }

            ProfileRegex.toDeepLink(uri)?.let {
                return@run DeepLinkDestination.ProfileTab
            }

            SecondScreenRegex.toDeepLink(uri)?.let {
                return@run DeepLinkDestination.SecondScreen
            }

            ThirdScreenRegexWithQueryParam.toDeepLink(uri)?.let {
                val argument = it.queryParameter("arg") ?: return null
                return@run DeepLinkDestination.ThirdScreen(argument)
            }

            ThirdScreenRegexWithPathParam.toDeepLink(uri)?.let {
                val argument = it.pathParameter("arg") ?: return null
                return@run DeepLinkDestination.ThirdScreen(argument)
            }
        }

        logger.i { "resolved deep link: $destination" }
        return destination
    }
}
