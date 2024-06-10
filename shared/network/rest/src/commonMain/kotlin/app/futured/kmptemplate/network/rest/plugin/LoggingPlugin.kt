package app.futured.kmptemplate.network.rest.plugin

import app.futured.kmptemplate.network.rest.logging.KtorKermitLogger
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import org.koin.core.annotation.Single

@Single
internal class LoggingPlugin : HttpClientPlugin {

    companion object {
        private val LOG_LEVEL = LogLevel.INFO
    }

    override fun install(config: HttpClientConfig<*>) {
        config.install(Logging) {
            this.level = LOG_LEVEL
            this.logger = KtorKermitLogger()
        }
    }
}
