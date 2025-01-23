package app.futured.kmptemplate.feature.navigation.deepLink

/**
 * This interface maps provided deep link Uri into [DeepLinkDestination].
 */
internal interface DeepLinkResolver {

    /**
     * Resolves provided [uri] into [DeepLinkDestination].
     *
     * @return Valid [DeepLinkDestination] if provided [uri] is valid and can be parsed by application, or `null`.
     */
    fun resolve(uri: String): DeepLinkDestination?
}
