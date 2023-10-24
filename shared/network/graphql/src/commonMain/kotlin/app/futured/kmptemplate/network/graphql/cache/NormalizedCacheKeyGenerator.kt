package app.futured.kmptemplate.network.graphql.cache

import app.futured.kmptemplate.network.graphql.cache.NormalizedCacheKeyGenerator.Companion.KNOWN_TYPES
import com.apollographql.apollo3.cache.normalized.api.CacheKey
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGenerator
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGeneratorContext
import com.apollographql.apollo3.cache.normalized.api.Record

private typealias Typename = String
private typealias IdField = String

/**
 * Cache key generator for types defined in GraphQL schema.
 * This cache key generator assumes that all types processed by the cache have an `id` field in Apollo response.
 *
 * When we want to normalize an entity in the cache and that entity must be uniquely identified
 * by other field other than `id`, we need to specify it's unique field in [KNOWN_TYPES] map.
 */
internal class NormalizedCacheKeyGenerator : CacheKeyGenerator {

    companion object {

        /**
         * Add all types that are known to have different unique identifier field than "id".
         */
        val KNOWN_TYPES: Map<Typename, IdField> = mapOf()

        const val DEFAULT_ID_FIELD: IdField = "id"
    }

    /**
     * Returns a [CacheKey] for the given object or null if the object doesn't have an id
     *
     * @param obj a [Map] representing the object. The values in the map can have the same types as the ones in [Record]
     * @param context the context in which the object is normalized.
     * In most use cases, the id should not depend on the normalization
     * context. Only use for advanced use cases.
     */
    override fun cacheKeyForObject(obj: Map<String, Any?>, context: CacheKeyGeneratorContext): CacheKey? {
        val typename = obj["__typename"] as? String ?: return null

        val idField = KNOWN_TYPES[typename] ?: DEFAULT_ID_FIELD
        val id = obj[idField] as? String ?: run {
            // logger.w { "Cache keygen miss for type: $typename, assumed ID field: $idField" }
            return null
        }

        return CacheKey(typename, id)
    }
}
