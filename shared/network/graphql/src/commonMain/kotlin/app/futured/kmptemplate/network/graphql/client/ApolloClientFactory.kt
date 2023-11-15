package app.futured.kmptemplate.network.graphql.client

import app.futured.kmptemplate.network.graphql.interceptor.LoggingInterceptorFactory
import app.futured.kmptemplate.network.graphql.tools.Constants
import app.futured.kmptemplate.network.graphql.cache.NetworkNormalizedCacheFactory
import app.futured.kmptemplate.network.graphql.cache.NormalizedCacheKeyGenerator
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.http.DefaultHttpEngine
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import org.koin.core.annotation.Single

@Single
class ApolloClientFactory internal constructor(
    private val loggingInterceptorFactory: LoggingInterceptorFactory,
    private val networkNormalizedCacheFactory: NetworkNormalizedCacheFactory,
    private val cacheKeyGenerator: NormalizedCacheKeyGenerator,
) {

    fun create(): ApolloClient = ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder()
                .httpEngine(DefaultHttpEngine(timeoutMillis = Constants.Configuration.REQUEST_TIMEOUT.inWholeMilliseconds))
                .serverUrl(Constants.Configuration.API_URL)
                .addInterceptor(loggingInterceptorFactory.create())
                .build(),
        )
        .normalizedCache(
            normalizedCacheFactory = networkNormalizedCacheFactory.create(),
            cacheKeyGenerator = cacheKeyGenerator,
        )
        .fetchPolicy(FetchPolicy.NetworkFirst)
        .build()
}
