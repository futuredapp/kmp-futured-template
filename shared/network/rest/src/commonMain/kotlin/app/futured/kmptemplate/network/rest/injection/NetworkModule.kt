package app.futured.kmptemplate.network.rest.injection

import app.futured.kmptemplate.network.rest.BuildKonfig
import app.futured.kmptemplate.network.rest.api.StarWarsApi
import app.futured.kmptemplate.network.rest.logging.KtorKermitLogger
import app.futured.kmptemplate.network.rest.result.NetworkResultConverterFactory
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("app.futured.kmptemplate.network.rest")
class NetworkRestModule {

    @Single
    @Named("apiUrl")
    fun apiUrl(): String = BuildKonfig.apiUrl

    @Single
    @Named("restApiJson")
    fun restApiJson(): Json = Json {
        isLenient = true
        prettyPrint = false
        ignoreUnknownKeys = true
    }

    @Single
    fun httpClient(@Named("restApiJson") json: Json): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(json = json, contentType = ContentType.Application.Json)
        }
        install(Logging) {
            this.level = LogLevel.INFO
            this.logger = KtorKermitLogger()
        }
    }

    @Single
    fun ktorFit(
        @Named("apiUrl") apiUrl: String,
        httpClient: HttpClient,
        converterFactory: NetworkResultConverterFactory,
    ): Ktorfit = Ktorfit.Builder()
        .baseUrl(apiUrl)
        .httpClient(httpClient)
        .converterFactories(converterFactory)
        .build()

    @Single
    fun starWarsApi(ktorFit: Ktorfit): StarWarsApi = ktorFit.create<StarWarsApi>()
}
