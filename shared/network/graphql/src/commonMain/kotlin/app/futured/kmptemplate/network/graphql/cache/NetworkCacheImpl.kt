package app.futured.kmptemplate.network.graphql.cache

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.apolloStore
import org.koin.core.annotation.Single

@Single
internal class NetworkCacheImpl(private val apolloClient: ApolloClient) : NetworkCache {

    override fun clear() {
        apolloClient.apolloStore.clearAll()
    }
}
