package app.futured.kmptemplate.network.rest.injection

import app.futured.kmptemplate.network.rest.api.ExampleApi
import app.futured.kmptemplate.network.rest.result.NetworkErrorParser
import app.futured.kmptemplate.network.rest.result.NetworkResultConverterFactory
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun networkRestModule() = module {

    single(named("apiUrl")) {
        "https://swapi.dev/api/"
    }

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
        }
    }

    single {
        Ktorfit.Builder()
            .baseUrl(get(named("apiUrl")))
            .httpClient(get<HttpClient>())
            .converterFactories(get<NetworkResultConverterFactory>())
            .build()
    }

    single { get<Ktorfit>().create<ExampleApi>() }
}
