package app.futured.kmptemplate.feature_v3.navigation.deepLink

import io.ktor.http.Url
import io.ktor.util.toMap

/**
 * An intermediate representation of deep link before it gets converted to [DeepLinkDestination].
 * Encapsulates data and logic needed for extracting path and query parameters from [uri].
 *
 * @param regex Regular expression that matches given deep link [uri].
 * @param uri Raw value of deeplink URI from which path and query parameters can be extracted.
 */
internal data class DeepLink(
    val regex: Regex,
    val uri: String,
) {

    companion object {
        fun from(regex: Regex, uri: String): DeepLink? {
            return if (regex.matches(uri)) {
                DeepLink(regex, uri)
            } else {
                null
            }
        }
    }

    inline fun pathParameter(key: String): String? {
        return regex.find(uri)?.groups?.get(key)?.value
    }

    inline fun queryParameter(key: String): String? {
        val params = Url(uri).parameters.toMap()
        return params[key]?.firstOrNull()
    }
}

/**
 * Converts [Regex] to [DeepLink].
 *
 * @return An instance of [DeepLink] if provided [uri] matches this [Regex], or `null`.
 */
internal fun Regex.toDeepLink(uri: String): DeepLink? = DeepLink.from(this, uri)
