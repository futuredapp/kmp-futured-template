package app.futured.kmptemplate.network.rest.ext

import app.futured.kmptemplate.network.rest.result.NetworkError
import io.ktor.http.HttpStatusCode

/**
 * Checks whether given [Throwable] is [NetworkError.ConnectionError]
 */
fun Throwable.isConnectionError(): Boolean = this is NetworkError.ConnectionError

fun Throwable.isUnauthorizedError(): Boolean =
    this is NetworkError.HttpError && this.statusCode == HttpStatusCode.Unauthorized.value
