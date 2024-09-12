package app.futured.kmptemplate.network.graphql.cache

import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import org.koin.core.annotation.Single

@Single
internal class NetworkNormalizedCacheFactory {

    companion object {
        private const val MEMORY_CACHE_SIZE_BYTES = 10 * 1024 * 1024 // 10 MB
        private const val SQL_CACHE_FILE = "kmptemplate_apollo_cache.db"
    }

    /**
     * Creates [NormalizedCacheFactory] for Apollo client by chaining
     * in-memory normalized cache and SQL cache.
     *
     * Whenever Apollo Kotlin attempts to read cached data,
     * it checks each chained cache in order until it encounters a hit.
     * It then immediately returns that cached data without reading any additional caches.
     *
     * Whenever Apollo Kotlin writes data to the cache, those writes propagate down all caches in the chain.
     */
    fun create(): NormalizedCacheFactory {
        val inMemoryCacheFactory = MemoryCacheFactory(MEMORY_CACHE_SIZE_BYTES)
        val sqlCacheFactory = SqlNormalizedCacheFactory(SQL_CACHE_FILE)

        return inMemoryCacheFactory.chain(sqlCacheFactory)
    }
}
