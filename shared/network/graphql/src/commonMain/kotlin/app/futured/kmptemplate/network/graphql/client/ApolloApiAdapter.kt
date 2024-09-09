package app.futured.kmptemplate.network.graphql.client

import app.futured.kmptemplate.network.graphql.result.NetworkError
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
internal class ApolloApiAdapter(
    private val apolloClient: ApolloClient,
    private val errorResponseParser: ErrorResponseParser,
) {

    /**
     * Executes the [Query] and returns the [DATA].
     *
     * @param query to be executed
     * @param fetchPolicy fetch policy to apply
     * @return [DATA] as a result of the [query]
     */
    suspend fun <DATA : Query.Data> executeQuery(query: Query<DATA>, fetchPolicy: FetchPolicy): DATA =
        executeOperation { apolloClient.query(query).fetchPolicy(fetchPolicy.asApolloFetchPolicy()).execute() }

    /**
     * Executes the [Query] and then watches it from normalized cache.
     *
     * @param query to be executed and watched.
     * @param fetchPolicy fetch policy to apply to initial fetch.
     * @param filterOutExceptions whether to filter out error responses (like cache misses) from the flow.
     * @return Flow of [DATA] wrapped in Kotlin result
     */
    fun <DATA : Query.Data> watchQueryWatcher(
        query: Query<DATA>,
        fetchPolicy: FetchPolicy,
        filterOutExceptions: Boolean,
    ): Flow<Result<DATA>> =
        apolloClient
            .query(query)
            .fetchPolicy(fetchPolicy.asApolloFetchPolicy())
            .watch()
            .filter {
                if (filterOutExceptions) {
                    it.exception == null
                } else {
                    true
                }
            }
            .map { apolloResponse -> runCatching { executeOperation { apolloResponse } } }


    /**
     * Executes the [Mutation] and returns the [DATA].
     *
     * @param mutation to be executed
     * @return [DATA] as a result of the [mutation]
     */
    suspend fun <DATA : Mutation.Data> executeMutation(mutation: Mutation<DATA>): DATA =
        executeOperation { apolloClient.mutation(mutation).execute() }

    private suspend fun <DATA : Operation.Data> executeOperation(
        apolloCall: suspend () -> ApolloResponse<DATA>,
    ): DATA {
        val result = runCatching {
            unfoldResult(apolloCall())
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
     * Unwraps successful [ApolloResponse] either into [DATA], or throws [NetworkError].
     * If the response contains data, it returns the data.
     * If the response contains an exception, it throws [NetworkError.UnknownError].
     * If the response contains errors, it throws [NetworkError.CloudError].
     */
    private fun <DATA : Operation.Data> unfoldResult(response: ApolloResponse<DATA>): DATA =
        response.data ?: run {
            response.exception?.let { exception ->
                throw NetworkError.UnknownError(exception)
            } ?: throw NetworkError.CloudError(
                code = errorResponseParser.getErrorResponseCode(response),
                message = errorResponseParser.getErrorMessage(response) ?: ""
            )
        }
}
