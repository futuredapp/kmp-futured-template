package app.futured.kmptemplate.network.rest.plugin

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
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

    /**
     * Custom Ktor logger that uses Kermit to log the logs 🪵.
     */
    private class KtorKermitLogger : Logger {

        private val logger = co.touchlab.kermit.Logger.withTag("Ktor")

        override fun log(message: String) = logger.d { message }
    }
}
