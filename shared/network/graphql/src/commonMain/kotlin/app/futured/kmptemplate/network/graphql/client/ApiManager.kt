package app.futured.kmptemplate.network.graphql.client

import app.futured.kmptemplate.network.graphql.ext.isUnauthorizedCloudError
import app.futured.kmptemplate.network.graphql.result.CloudErrorCode
import app.futured.kmptemplate.network.graphql.result.NetworkError
import app.futured.kmptemplate.network.graphql.result.NetworkResult
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Query
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This interface is responsible for communication with GraphQL cloud via queries and mutations.
 * It handles the communication, but requests and responses should be defined by the caller class.
 *
 * The responses are wrapped with [NetworkResult].
 */
internal interface ApiManager {

    val apiAdapter: ApolloApiAdapter

    /**
     * Executes the [Query] and returns the [DATA] response wrapped with [NetworkResult].
     *
     * @param query to be executed
     * @param fetchPolicy fetch policy to apply to this query
     * @param throwOnPartialData whether to throw an exception on partial data
     * @return the result of the [query] wrapped with [NetworkResult]
     */
    suspend fun <DATA : Query.Data> executeQuery(
        query: Query<DATA>,
        fetchPolicy: FetchPolicy = FetchPolicy.NetworkOnly,
        throwOnPartialData: Boolean = false,
    ): NetworkResult<DATA> =
        runWrapping { errorInterceptor { apiAdapter.executeQuery(query, fetchPolicy, throwOnPartialData) } }

    /**
     * Executes the [Query] and then watches it from normalized cache.
     * Returns Flow of [DATA] wrapped with [NetworkResult].
     *
     * @param query to be executed and watched.
     * @param fetchPolicy fetch policy to apply to initial fetch.
     * @param filterOutExceptions whether to filter out exceptions from the flow.
     * @param throwOnPartialData whether to throw an exception on partial data
     * @return Flow of [DATA] wrapped in [NetworkResult]
     */
    fun <DATA : Query.Data> executeQueryWatcher(
        query: Query<DATA>,
        fetchPolicy: FetchPolicy,
        filterOutExceptions: Boolean = false,
        throwOnPartialData: Boolean = false,
    ): Flow<NetworkResult<DATA>> = apiAdapter
        .watchQueryWatcher(query, fetchPolicy, filterOutExceptions, throwOnPartialData)
        .map { result -> runWrapping { errorInterceptor { result.getOrThrow() } } }

    /**
     * Executes the [Mutation] and returns the [DATA] response wrapped with [NetworkResult].
     *
     * @param mutation to be executed
     * @param throwOnPartialData whether to throw an exception on partial data
     * @return the result of the [mutation] wrapped with [NetworkResult]
     */
    suspend fun <DATA : Mutation.Data> executeMutation(mutation: Mutation<DATA>, throwOnPartialData: Boolean = false): NetworkResult<DATA> =
        runWrapping { errorInterceptor { apiAdapter.executeMutation(mutation, throwOnPartialData) } }

    /**
     * Executes [operation], wrapping result in [NetworkResult].
     * Rethrows any [CancellationException] as is. This is a standard way of handling cancellation in coroutines.
     */
    @Suppress("TooGenericExceptionCaught")
    private suspend fun <DATA> runWrapping(operation: suspend () -> DATA): NetworkResult<DATA> = try {
        NetworkResult.Success(operation())
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (networkError: NetworkError) {
        NetworkResult.Failure(networkError)
    } catch (throwable: Throwable) {
        NetworkResult.Failure(NetworkError.UnknownError(throwable))
    }

    /**
     * You can use this to intercept errors before thrown to upper layers.
     * Eg. for force logging out user on [CloudErrorCode.Unauthorized], etc.
     */
    private suspend fun <RESULT> errorInterceptor(operation: suspend () -> RESULT): RESULT = try {
        operation()
    } catch (error: NetworkError.CloudError) {
        if (error.isUnauthorizedCloudError()) {
            // Force log out user here, via some relay interface
            // logoutRelay.onUserUnauthorizedError()
            throw error
        } else {
            throw error
        }
    }
}
