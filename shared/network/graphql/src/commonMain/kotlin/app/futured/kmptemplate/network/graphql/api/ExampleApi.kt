package app.futured.kmptemplate.network.graphql.api

import app.futured.kmpfuturedtemplate.network.graphql.fragment.EpisodeFragment
import app.futured.kmptemplate.network.graphql.result.NetworkResult

interface ExampleApi {
    suspend fun getUser(): NetworkResult<List<EpisodeFragment>>
}
