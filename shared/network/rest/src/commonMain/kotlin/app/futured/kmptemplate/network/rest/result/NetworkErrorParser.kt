package app.futured.kmptemplate.network.rest.result

import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

/**
 * Class responsible for converting [Throwable]s into meaningful [NetworkError]s.
 */
internal class NetworkErrorParser {

    /**
     * Parses provided [throwable] into [NetworkError].
     */
    fun parse(throwable: Throwable): NetworkError = when (throwable) {
        is SerializationException -> NetworkError.SerializationError(throwable)
        is IOException, is UnresolvedAddressException -> NetworkError.ConnectionError(throwable)
        else -> NetworkError.UnknownError(throwable)
    }

    /**
     * Parses provided [code] as [NetworkError.HttpError]
     */
    fun parse(code: HttpStatusCode): NetworkError = if (!code.isSuccess()) {
        NetworkError.HttpError(statusCode = code)
    } else {
        error("The provided code: $code is successful and cannot be parsed as error")
    }
}
