package app.futured.kmptemplate.network.graphql.client

import app.futured.kmptemplate.network.api.graphql.result.NetworkError
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.watch
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

/**
 * The class responsible for communication directly with [ApolloClient] via GraphQL queries and mutations.
 */
@Single
internal class ApolloApiAdapter(private val apolloClient: ApolloClient, private val errorResponseParser: ErrorResponseParser) {

    /**
     * Executes the [Query] and returns the [DATA].
     *
     * @param query to be executed
     * @param fetchPolicy fetch policy to apply
     * @param throwOnPartialData whether to throw an exception on partial data
     * @return [DATA] as a result of the [query]
     */
    suspend fun <DATA : Query.Data> executeQuery(query: Query<DATA>, fetchPolicy: FetchPolicy, throwOnPartialData: Boolean): DATA =
        executeOperation(throwOnPartialData) { apolloClient.query(query).fetchPolicy(fetchPolicy.asApolloFetchPolicy()).execute() }

    /**
     * Executes the [Query] and then watches it from normalized cache.
     *
     * @param query to be executed and watched.
     * @param filterOutExceptions whether to filter out exceptions from the flow.
     * @param throwOnPartialData whether to throw an exception on partial data
     * @param fetchPolicy fetch policy to apply to initial fetch.
     * @return Flow of [DATA] wrapped in Kotlin result
     */
    fun <DATA : Query.Data> watchQueryWatcher(
        query: Query<DATA>,
        fetchPolicy: FetchPolicy,
        filterOutExceptions: Boolean,
        throwOnPartialData: Boolean,
    ): Flow<Result<DATA>> =
        apolloClient
            .query(query)
            .fetchPolicy(fetchPolicy.asApolloFetchPolicy())
            .watch()
            .filter { if (filterOutExceptions) it.exception == null else true }
            .map { apolloResponse -> runCatching { executeOperation(throwOnPartialData) { apolloResponse } } }

    /**
     * Executes the [Mutation] and returns the [DATA].
     *
     * @param mutation to be executed
     * @param throwOnPartialData whether to throw an exception on partial data
     * @return [DATA] as a result of the [mutation]
     */
    suspend fun <DATA : Mutation.Data> executeMutation(mutation: Mutation<DATA>, throwOnPartialData: Boolean): DATA =
        executeOperation(throwOnPartialData) { apolloClient.mutation(mutation).execute() }

    private suspend fun <DATA : Operation.Data> executeOperation(
        throwOnPartialData: Boolean,
        apolloCall: suspend () -> ApolloResponse<DATA>,
    ): DATA {
        val result = runCatching {
            unfoldResult(apolloCall(), throwOnPartialData)
        }

        result.fold(
            onSuccess = { data ->
                return data
            },
            onFailure = { throwable ->
                throw when (throwable) {
                    is ApolloNetworkException -> NetworkError.ConnectionError(
                        message = throwable.message ?: throwable.cause?.message,
                        cause = throwable.cause,
                    )

                    is ApolloHttpException -> NetworkError.CloudHttpError(
                        httpCode = throwable.statusCode,
                        message = throwable.message ?: throwable.cause?.message,
                    )

                    else -> throwable
                }
            },
        )
    }

    /**
     * https://www.apollographql.com/docs/kotlin/essentials/errors#truth-table
     * Unwraps successful [ApolloResponse] either into [DATA], or throws [NetworkError].
     * If the response contains an exception, it throws the exception.
     * If the response contains complete data, it returns the data.
     * If the response contains partial data, it returns the data or throws an exception (It's possible to have both
     * [ApolloResponse.data] non-null and [ApolloResponse.errors] non-null.)
     * If the response contains errors, it throws [NetworkError.CloudError].
     * @param throwOnPartialData whether to throw an exception on partial data
     */
    private fun <DATA : Operation.Data> unfoldResult(
        response: ApolloResponse<DATA>,
        throwOnPartialData: Boolean,
    ): DATA {
        response.exception?.let { throw it }

        response.data?.let { data ->
            if (response.hasErrors()) {
                if (throwOnPartialData) {
                    throw NetworkError.CloudError(
                        code = errorResponseParser.getErrorResponseCode(response),
                        message = errorResponseParser.getErrorMessage(response),
                    )
                }
                // Return partial data
                return data
            }
            // Return complete data
            return data
        }

        // Non-compliant server, if data is null, there should be at least one error, or
        // A GraphQL request error happened or a Graph field error bubbled up.
        throw NetworkError.CloudError(
            code = errorResponseParser.getErrorResponseCode(response),
            message = errorResponseParser.getErrorMessage(response),
        )
    }
}
