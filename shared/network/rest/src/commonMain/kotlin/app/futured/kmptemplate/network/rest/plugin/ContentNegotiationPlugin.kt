package app.futured.kmptemplate.network.rest.plugin

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
internal class ContentNegotiationPlugin(@Named("restApiJson") private val json: Json) : HttpClientPlugin {

    override fun install(config: HttpClientConfig<*>) {
        config.install(ContentNegotiation) {
            json(json = json, contentType = ContentType.Application.Json)
        }

        // https://github.com/Foso/Ktorfit/issues/94
        config.install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}
