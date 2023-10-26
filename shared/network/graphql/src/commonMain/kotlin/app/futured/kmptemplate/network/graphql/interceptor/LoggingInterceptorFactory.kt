package app.futured.kmptemplate.network.graphql.interceptor

import co.touchlab.kermit.Logger
import com.apollographql.apollo3.network.http.LoggingInterceptor

internal class LoggingInterceptorFactory {

    fun create(): LoggingInterceptor {
        val logger = Logger.withTag("Apollo")
        return LoggingInterceptor(
            level = LoggingInterceptor.Level.BASIC,
            log = { log -> logger.d { log } },
        )
    }
}
