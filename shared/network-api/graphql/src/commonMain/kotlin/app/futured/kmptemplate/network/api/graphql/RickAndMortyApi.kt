package app.futured.kmptemplate.network.api.graphql

import app.futured.kmptemplate.network.api.graphql.fragment.EpisodeFragment
import app.futured.kmptemplate.network.api.graphql.result.NetworkResult

interface RickAndMortyApi {
    suspend fun getEpisodes(): NetworkResult<List<EpisodeFragment>>
}
