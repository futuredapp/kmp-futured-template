package app.futured.kmptemplate.network.rest.plugin

import app.futured.kmptemplate.network.rest.FlavorConstants
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.api.createClientPlugin
import org.koin.core.annotation.Single

@Single
internal class ApiKeyPlugin : HttpClientPlugin {
    override fun install(config: HttpClientConfig<*>) {
        config.install(
            createClientPlugin("ApiKeyPlugin") {
                onRequest { request, content ->
                    val isRestUlr = request.url.toString().startsWith(FlavorConstants.apiUrl)
                    if (isRestUlr) {
                        request.headers.append("x-api-key", FlavorConstants.apiKey)
                    }
                }
            },
        )
    }
}
