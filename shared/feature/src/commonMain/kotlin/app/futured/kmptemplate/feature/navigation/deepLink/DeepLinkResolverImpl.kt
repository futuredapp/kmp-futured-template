package app.futured.kmptemplate.feature.navigation.deepLink

import co.touchlab.kermit.Logger
import org.koin.core.annotation.Single

@Single
internal class DeepLinkResolverImpl : DeepLinkResolver {

    companion object {
        private val HomeRegex = "kmptemplate://home".toRegex()
    }

    private val logger = Logger.withTag("DeepLinkResolverImpl")

    override fun resolve(uri: String): DeepLinkDestination? {
        logger.i { "resolving deep link: $uri" }
        val destination = run {
            HomeRegex.toDeepLink(uri)?.let {
                return@run DeepLinkDestination.HomeTab
            }


        }

        logger.i { "resolved deep link: $destination" }
        return destination
    }
}
