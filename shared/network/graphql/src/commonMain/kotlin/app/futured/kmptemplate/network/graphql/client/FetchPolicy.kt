package app.futured.kmptemplate.network.graphql.client

import com.apollographql.apollo3.exception.ApolloCompositeException
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.CacheMissException
import com.apollographql.apollo3.cache.normalized.FetchPolicy as ApolloFetchPolicy

enum class FetchPolicy {
    /**
     * Try the cache, if that failed, try the network.
     *
     * An [ApolloCompositeException] is thrown if the data is not in the cache and the network call failed.
     * If coming from the cache 1 value is emitted, otherwise 1 or multiple values can be emitted from the network.
     *
     * This is the default behaviour.
     */
    CacheFirst,

    /**
     * Only try the cache.
     *
     * A [CacheMissException] is thrown if the data is not in the cache, otherwise 1 value is emitted.
     */
    CacheOnly,

    /**
     * Try the network, if that failed, try the cache.
     *
     * An [ApolloCompositeException] is thrown if the network call failed and the data is not in the cache.
     * If coming from the network 1 or multiple values can be emitted, otherwise 1 value is emitted from the cache.
     */
    NetworkFirst,

    /**
     * Only try the network.
     *
     * An [ApolloException] is thrown if the network call failed, otherwise 1 or multiple values can be emitted.
     */
    NetworkOnly,

    /**
     * Try the cache, then also try the network.
     *
     * If the data is in the cache, it is emitted, if not, no exception is thrown at that point. Then the network call is made, and if
     * successful the value(s) are emitted, otherwise either an [ApolloCompositeException] (both cache miss and network failed) or an
     * [ApolloException] (only network failed) is thrown.
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
