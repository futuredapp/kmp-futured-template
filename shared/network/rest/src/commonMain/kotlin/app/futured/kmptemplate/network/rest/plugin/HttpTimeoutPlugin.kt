package app.futured.kmptemplate.network.rest.plugin

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.seconds

@Single
internal class HttpTimeoutPlugin : HttpClientPlugin {

    companion object {
        private val CONNECT_TIMEOUT = 10.seconds
        private val REQUEST_TIMEOUT = 15.seconds
        private val SOCKET_TIMEOUT = 10.seconds
    }

    override fun install(config: HttpClientConfig<*>) {
        config.install(HttpTimeout) {
            connectTimeoutMillis = CONNECT_TIMEOUT.inWholeMilliseconds
            requestTimeoutMillis = REQUEST_TIMEOUT.inWholeMilliseconds
            socketTimeoutMillis = SOCKET_TIMEOUT.inWholeMilliseconds
        }
    }
}
