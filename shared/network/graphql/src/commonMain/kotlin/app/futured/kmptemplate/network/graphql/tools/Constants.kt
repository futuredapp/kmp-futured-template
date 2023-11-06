package app.futured.kmptemplate.network.graphql.tools

import app.futured.kmpfuturedtemplate.network.graphql.BuildKonfig
import kotlin.time.Duration.Companion.seconds

internal object Constants {

    object Configuration {
        val API_URL = BuildKonfig.apiUrl
        val REQUEST_TIMEOUT = 20.seconds
    }
}
