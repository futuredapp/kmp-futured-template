package app.futured.kmptemplate.network.graphql.client

import app.futured.kmptemplate.network.graphql.result.CloudErrorCode
import com.apollographql.apollo3.api.ApolloResponse
import org.koin.core.annotation.Single

/**
 * Class responsible for parsing of error properties from [ApolloResponse].
 */
@Single
internal class ErrorResponseParser {

    private companion object {
        const val CODE_KEY = "code"
    }

    /**
     * Gets response code or null. This property is parsed from response.errors.extensions.code property.
     */
    fun getErrorResponseCode(response: ApolloResponse<*>): CloudErrorCode {
        val error = response.errors?.firstOrNull()
        return CloudErrorCode.fromExtensionCode(error?.extensions?.get(CODE_KEY) as? String)
    }

    /**
     * Gets response message or null. This property is parsed from response.errors.message property.
     */
    fun getErrorMessage(response: ApolloResponse<*>): String? {
        val error = response.errors?.firstOrNull()
        return error?.message
    }
}
