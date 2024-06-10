package app.futured.kmptemplate.network.rest.tools

import kotlin.time.Duration.Companion.seconds

object Constants {

    internal object Timeouts {
        val CONNECT_TIMEOUT = 10.seconds
        val REQUEST_TIMEOUT = 15.seconds
        val SOCKET_TIMEOUT = 10.seconds
    }
}
