package app.futured.kmptemplate.network.rest.logging

import io.ktor.client.plugins.logging.Logger
import co.touchlab.kermit.Logger.Companion as KermitLogger

/**
 * Custom Ktor logger that uses Kermit to log the logs 🪵.
 */
internal class KtorKermitLogger : Logger {

    private val logger = KermitLogger.withTag("Ktor")

    override fun log(message: String) = logger.d { message }
}
