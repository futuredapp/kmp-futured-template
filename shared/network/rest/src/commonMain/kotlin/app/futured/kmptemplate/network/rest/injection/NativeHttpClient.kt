package app.futured.kmptemplate.network.rest.injection

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

/**
 * Returns platform-native HTTP client for Ktor framework.
 */
expect fun getNativeHttpClient(configure: HttpClientConfig<*>.() -> Unit): HttpClient
