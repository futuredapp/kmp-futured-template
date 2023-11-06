package app.futured.kmptemplate.network.rest.injection

import app.futured.kmpfuturedtemplate.network.rest.BuildKonfig
import app.futured.kmptemplate.network.rest.api.StarWarsApi
import app.futured.kmptemplate.network.rest.logging.KtorKermitLogger
import app.futured.kmptemplate.network.rest.result.NetworkErrorParser
import app.futured.kmptemplate.network.rest.result.NetworkResultConverterFactory
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.Logger
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun networkRestModule() = module {

    single(named("apiUrl")) { BuildKonfig.apiUrl }

    single(named("restApiJson")) {
        Json {
            isLenient = true
            prettyPrint = false
            ignoreUnknownKeys = true
        }
    }

    singleOf(::NetworkErrorParser)
    singleOf(::NetworkResultConverterFactory)

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = get(named("restApiJson")), contentType = ContentType.Application.Json)
            }
            install(Logging) {
                this.level = LogLevel.INFO
                this.logger = KtorKermitLogger()
            }
        }
    }

    single {
        Ktorfit.Builder()
            .baseUrl(get(named("apiUrl")))
            .httpClient(get<HttpClient>())
            .converterFactories(get<NetworkResultConverterFactory>())
            .build()
    }

    single { get<Ktorfit>().create<StarWarsApi>() }
}
