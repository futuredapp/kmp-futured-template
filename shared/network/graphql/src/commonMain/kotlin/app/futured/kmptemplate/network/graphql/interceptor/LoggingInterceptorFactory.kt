package app.futured.kmptemplate.network.graphql.interceptor

import com.apollographql.apollo3.network.http.LoggingInterceptor

internal class LoggingInterceptorFactory {

    fun create(): LoggingInterceptor {
        // TODO create a logger of your choice (for example Kermit `Logger.withTag("ApolloClient")`) and use it in `log` lambda
        return LoggingInterceptor(
            level = LoggingInterceptor.Level.HEADERS,
            log = { log -> println(log) },
        )
    }
}
