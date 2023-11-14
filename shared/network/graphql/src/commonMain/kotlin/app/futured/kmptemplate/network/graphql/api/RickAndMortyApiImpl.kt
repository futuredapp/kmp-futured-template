package app.futured.kmptemplate.network.graphql.api

import app.futured.kmptemplate.network.graphql.GetEpisodesQuery
import app.futured.kmptemplate.network.graphql.client.ApiManager
import app.futured.kmptemplate.network.graphql.client.ApolloApiAdapter
import app.futured.kmptemplate.network.graphql.fragment.EpisodeFragment
import app.futured.kmptemplate.network.graphql.result.NetworkResult
import app.futured.kmptemplate.network.graphql.result.map

internal class RickAndMortyApiImpl(
    override val apiAdapter: ApolloApiAdapter,
) : RickAndMortyApi, ApiManager {

    override suspend fun getEpisodes(): NetworkResult<List<EpisodeFragment>> = executeQuery(GetEpisodesQuery())
        .map { data ->
            val results = data.episodes?.results ?: emptyList()
            results.mapNotNull { result -> result?.episodeFragment }
        }
}
