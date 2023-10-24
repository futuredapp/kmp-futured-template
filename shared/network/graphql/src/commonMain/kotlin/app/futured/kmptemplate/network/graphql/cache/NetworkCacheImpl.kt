package app.futured.kmptemplate.network.graphql.cache

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.apolloStore

internal class NetworkCacheImpl(
    private val apolloClient: ApolloClient,
) : NetworkCache {

    override fun clear() {
        apolloClient.apolloStore.clearAll()
    }
}
