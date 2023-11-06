package app.futured.kmptemplate.network.graphql.ext

import app.futured.kmptemplate.network.graphql.result.CloudErrorCode
import app.futured.kmptemplate.network.graphql.result.NetworkError

/**
 * Checks whether given [Throwable] is [NetworkError.CloudError] with Unauthorized code.
 */
fun Throwable.isUnauthorizedCloudError(): Boolean = this is NetworkError.CloudError && code is CloudErrorCode.Unauthorized

/**
 * Checks whether given [Throwable] is [NetworkError.CloudError] with Not found code.
 */
fun Throwable.isNotFoundCloudError(): Boolean = this is NetworkError.CloudError && code is CloudErrorCode.NotFound

/**
 * Checks whether given [Throwable] is [NetworkError.ConnectionError]
 */
fun Throwable.isConnectionError(): Boolean = this is NetworkError.ConnectionError
