package app.futured.kmptemplate.network.rest.injection

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient

/**
 * Returns platform-native HTTP client for Ktor framework.
 */
actual fun getNativeHttpClient(configure: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(OkHttp) {
    configure()
    engine {
        preconfigured = getOkHttpClient()
    }
}

private fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
