package app.futured.kmptemplate.network.rest.result

import io.ktor.http.HttpStatusCode

/**
 * Network error wrapper that encapsulate all the errors that occurred during execution of the network module operations.
 */
sealed class NetworkError(
    message: String?,
    cause: Throwable?,
) : RuntimeException(message, cause) {

    /**
     * Represents an error that occurred on the cloud but the HTTP response code was not successful.
     */
    class HttpError(
        val statusCode: Int,
        message: String?,
    ) : NetworkError(message = message, cause = null) {

        internal constructor(statusCode: HttpStatusCode) : this(statusCode = statusCode.value, message = statusCode.description)
    }

    class SerializationError(
        cause: Throwable?,
    ) : NetworkError(message = cause?.message, cause = cause)

    /**
     * Represents network error occurred during the network communication.
     * For example socket closed, DNS issue, TLS problem etc.
     */
    class ConnectionError(
        cause: Throwable?,
    ) : NetworkError(message = cause?.message, cause = cause)

    /**
     * Error class that should be used only for unknown network module errors.
     * Ideally, this error should be never thrown and each error type should use it's own [NetworkError] subclass.
     */
    class UnknownError(
        cause: Throwable?,
    ) : NetworkError(message = cause?.message, cause)
}
