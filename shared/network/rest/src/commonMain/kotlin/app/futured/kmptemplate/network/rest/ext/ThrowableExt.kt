package app.futured.kmptemplate.network.rest.ext

import app.futured.kmptemplate.network.api.rest.result.NetworkError

/**
 * Checks whether given [Throwable] is [NetworkError.ConnectionError]
 */
fun Throwable.isConnectionError(): Boolean = this is NetworkError.ConnectionError
