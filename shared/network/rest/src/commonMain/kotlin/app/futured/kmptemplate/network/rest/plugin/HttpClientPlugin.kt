package app.futured.kmptemplate.network.rest.plugin

import io.ktor.client.HttpClientConfig

/**
 * This interface unifies Ktor plugin installation logic. All HTTP client plugins should implement this interface.
 */
internal interface HttpClientPlugin {

    /**
     * Installs plugin into Ktor HTTP client's [config].
     */
    fun install(config: HttpClientConfig<*>)
}
