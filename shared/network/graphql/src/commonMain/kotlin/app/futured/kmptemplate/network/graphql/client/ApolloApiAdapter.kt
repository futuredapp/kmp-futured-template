package app.futured.kmptemplate.network.graphql.client

import app.futured.kmptemplate.network.graphql.result.NetworkError
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import kotlinx.coroutines.flow.Flow
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
     * @param fetchThrows whether to throw if an [ApolloException] happens during the initial fetch.
     * @param refetchThrows whether to throw if an [ApolloException] happens during a refetch.
     * @return Flow of [DATA] wrapped in Kotlin result
     */
    fun <DATA : Query.Data> watchQueryWatcher(
        query: Query<DATA>,
        fetchPolicy: FetchPolicy,
        fetchThrows: Boolean = true,
        refetchThrows: Boolean = false,
    ): Flow<Result<DATA>> =
        apolloClient
            .query(query)
            .fetchPolicy(fetchPolicy.asApolloFetchPolicy())
            .watch(fetchThrows = fetchThrows, refetchThrows = refetchThrows)
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
     * Unwraps successful [ApolloResponse] either into [DATA], or throws [NetworkError.CloudError]
     * in case errors are present in the response.
     */
    private fun <DATA : Operation.Data> unfoldResult(response: ApolloResponse<DATA>): DATA =
        if (response.hasErrors().not()) {
            response.data ?: error("Response without errors and with no data")
        } else {
            throw NetworkError.CloudError(
                code = errorResponseParser.getErrorResponseCode(response),
                message = errorResponseParser.getErrorMessage(response) ?: "",
            )
        }
}
