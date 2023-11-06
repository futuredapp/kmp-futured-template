package app.futured.kmptemplate.network.graphql.tools

import kotlin.time.Duration.Companion.seconds

internal object Constants {

    object Configuration {
        const val API_URL = "https://rickandmortyapi.com/graphql"
        val REQUEST_TIMEOUT = 20.seconds
    }
}
