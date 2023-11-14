package app.futured.kmptemplate.network.graphql.injection

import app.futured.kmptemplate.network.graphql.api.RickAndMortyApi
import app.futured.kmptemplate.network.graphql.api.RickAndMortyApiImpl
import app.futured.kmptemplate.network.graphql.cache.NetworkCache
import app.futured.kmptemplate.network.graphql.cache.NetworkCacheImpl
import app.futured.kmptemplate.network.graphql.cache.NetworkNormalizedCacheFactory
import app.futured.kmptemplate.network.graphql.cache.NormalizedCacheKeyGenerator
import app.futured.kmptemplate.network.graphql.client.ApolloApiAdapter
import app.futured.kmptemplate.network.graphql.client.ApolloClientFactory
import app.futured.kmptemplate.network.graphql.client.ErrorResponseParser
import app.futured.kmptemplate.network.graphql.interceptor.LoggingInterceptorFactory
import com.apollographql.apollo3.ApolloClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun networkGraphqlModule() = module {
    // region Internal API

    singleOf(::ErrorResponseParser)
    singleOf(::ApolloApiAdapter)

    singleOf(::LoggingInterceptorFactory)
    singleOf(::NetworkNormalizedCacheFactory)
    singleOf(::NormalizedCacheKeyGenerator)
    singleOf(::ApolloClientFactory)

    single<ApolloClient> { get<ApolloClientFactory>().create() }

    // endregion

    // region Public API

    singleOf(::RickAndMortyApiImpl) bind RickAndMortyApi::class
    singleOf(::NetworkCacheImpl) bind NetworkCache::class

// endregion
}
