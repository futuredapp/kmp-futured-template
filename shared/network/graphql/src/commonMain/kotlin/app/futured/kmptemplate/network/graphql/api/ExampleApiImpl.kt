package app.futured.kmptemplate.network.graphql.api

import app.futured.kmpfuturedtemplate.network.graphql.GetEpisodesQuery
import app.futured.kmpfuturedtemplate.network.graphql.fragment.EpisodeFragment
import app.futured.kmptemplate.network.graphql.client.ApiManager
import app.futured.kmptemplate.network.graphql.client.ApolloApiAdapter
import app.futured.kmptemplate.network.graphql.result.NetworkResult
import app.futured.kmptemplate.network.graphql.result.map

internal class ExampleApiImpl(
    override val apiAdapter: ApolloApiAdapter,
) : ExampleApi, ApiManager {

    override suspend fun getUser(): NetworkResult<List<EpisodeFragment>> = executeQuery(GetEpisodesQuery())
        .map { data ->
            val results = data.episodes?.results ?: emptyList()
            results.mapNotNull { result -> result?.episodeFragment }
        }
}
