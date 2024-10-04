package app.futured.kmptemplate.network.graphql.client

import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.cache.normalized.FetchPolicy as ApolloFetchPolicy

enum class FetchPolicy {
    /**
     * Try the cache, if that failed, try the network.
     *
     * This [FetchPolicy] emits one or more [ApolloResponse].
     * Cache misses and network errors have [ApolloResponse.exception] set to a non-null [ApolloException].
     *
     * This is the default behaviour.
     */
    CacheFirst,

    /**
     * Only try the cache.
     *
     * This [FetchPolicy] emits one [ApolloResponse].
     * Cache misses have [ApolloResponse.exception] set to a non-null [ApolloException].
     */
    CacheOnly,

    /**
     * Try the network, if that failed, try the cache.
     *
     * This [FetchPolicy] emits one or more [ApolloResponse].
     * Cache misses and network errors have [ApolloResponse.exception] set to a non-null [ApolloException].
     */
    NetworkFirst,

    /**
     * Only try the network.
     *
     * This FetchPolicy emits one or more [ApolloResponse].
     * Several [ApolloResponse] may be emitted if your NetworkTransport supports it, for example with @defer.
     * Network errors have [ApolloResponse.exception] set to a non-null[ApolloException].
     */
    NetworkOnly,

    /**
     * Try the cache, then also try the network.
     *
     * This FetchPolicy emits two or more [ApolloResponse].
     * Cache misses and network errors have [ApolloResponse.exception] set to a non-null [ApolloException].
     */
    CacheAndNetwork,
}

internal fun FetchPolicy.asApolloFetchPolicy(): ApolloFetchPolicy = when (this) {
    FetchPolicy.CacheFirst -> ApolloFetchPolicy.CacheFirst
    FetchPolicy.CacheOnly -> ApolloFetchPolicy.CacheOnly
    FetchPolicy.NetworkFirst -> ApolloFetchPolicy.NetworkFirst
    FetchPolicy.NetworkOnly -> ApolloFetchPolicy.NetworkOnly
    FetchPolicy.CacheAndNetwork -> ApolloFetchPolicy.CacheAndNetwork
}
