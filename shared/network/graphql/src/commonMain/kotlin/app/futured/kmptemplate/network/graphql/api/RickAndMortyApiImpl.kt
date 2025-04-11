package app.futured.kmptemplate.network.graphql.api

import app.futured.kmptemplate.network.graphql.api.GetEpisodesQuery
import app.futured.kmptemplate.network.api.graphql.RickAndMortyApi
import app.futured.kmptemplate.network.graphql.client.ApiManager
import app.futured.kmptemplate.network.graphql.client.ApolloApiAdapter
import app.futured.kmptemplate.network.graphql.api.fragment.EpisodeFragment
import app.futured.kmptemplate.network.api.graphql.result.NetworkResult
import app.futured.kmptemplate.network.api.graphql.result.map
import org.koin.core.annotation.Single

@Single
internal class RickAndMortyApiImpl(override val apiAdapter: ApolloApiAdapter) :
    RickAndMortyApi,
    ApiManager {

    override suspend fun getEpisodes(): NetworkResult<List<EpisodeFragment>> = executeQuery(GetEpisodesQuery())
        .map { data ->
            val results = data.episodes?.results ?: emptyList()
            results.mapNotNull { result -> result?.episodeFragment }
        }
}
