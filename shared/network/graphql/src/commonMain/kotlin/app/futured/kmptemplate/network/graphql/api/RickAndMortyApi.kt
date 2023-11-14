package app.futured.kmptemplate.network.graphql.api

import app.futured.kmptemplate.network.graphql.fragment.EpisodeFragment
import app.futured.kmptemplate.network.graphql.result.NetworkResult

interface RickAndMortyApi {
    suspend fun getEpisodes(): NetworkResult<List<EpisodeFragment>>
}
