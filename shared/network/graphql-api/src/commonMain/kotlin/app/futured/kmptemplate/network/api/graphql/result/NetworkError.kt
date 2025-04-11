package app.futured.kmptemplate.network.api.graphql.result

/**
 * Network error wrapper that encapsulate all the errors that occurred during execution of the network module operations.
 */
sealed class NetworkError(message: String?, cause: Throwable?) : RuntimeException(message, cause) {

    /**
     * Represents an error that occurred on the cloud and was returned as a response.
     * For example authentication error, validation error, invalid args etc.
     */
    class CloudError(val code: CloudErrorCode, message: String?) : NetworkError(message, cause = null) {

        /**
         * This method is implemented for easier debugging in the test environment.
         */
        override fun toString(): String = "Code: $code; Message: $message"
    }

    /**
     * Represents an error that occurred on the cloud but the response code was not 200
     * For example lower level error on cloud as service unavailable, etc.
     */
    class CloudHttpError(val httpCode: Int, message: String?) : NetworkError(message, cause = null)

    /**
     * Represents network error occurred during the network communication.
     * For example socket closed, DNS issue, TLS problem etc.
     */
    class ConnectionError(message: String?, cause: Throwable?) : NetworkError(message, cause)

    /**
     * Error class that should be used only for unknown network module errors.
     * Ideally, this error should be never thrown and each error type should use it's own [NetworkError] subclass
     */
    class UnknownError(cause: Throwable?) : NetworkError(message = null, cause) {

        /**
         * This method is implemented for easier debugging in the test environment.
         */
        override fun toString(): String = "Unknown error. Cause: $cause"
    }
}
