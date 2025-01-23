package app.futured.kmptemplate.network.graphql.interceptor

import co.touchlab.kermit.Logger
import com.apollographql.apollo.network.http.LoggingInterceptor
import org.koin.core.annotation.Single

@Single
internal class LoggingInterceptorFactory {

    fun create(): LoggingInterceptor {
        val logger = Logger.withTag("Apollo")
        return LoggingInterceptor(
            level = LoggingInterceptor.Level.BASIC,
            log = { log -> logger.d { log } },
        )
    }
}
