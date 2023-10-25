package app.futured.kmptemplate.network.rest.result

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.internal.TypeData
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.cast
import io.ktor.client.statement.HttpResponse as KtorHttpResponse

/**
 * This converter allows usage of [NetworkResult] as API return type.
 */
internal class NetworkResultConverterFactory(
    private val errorParser: NetworkErrorParser,
) : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit,
    ): Converter.SuspendResponseConverter<KtorHttpResponse, *>? {
        if (typeData.typeInfo.type == NetworkResult::class) {
            return object : Converter.SuspendResponseConverter<KtorHttpResponse, Any> {

                override suspend fun convert(response: KtorHttpResponse): Any = convert(KtorfitResult.Success(response))

                override suspend fun convert(result: KtorfitResult): Any {
                    val wrappedTypeInfo = typeData.typeArgs.first().typeInfo // NetworkResult<wrappedTypeInfo>

                    return when (result) {
                        is KtorfitResult.Success -> result.response.toNetworkResult(expectedType = wrappedTypeInfo)
                        is KtorfitResult.Failure -> NetworkResult.error(errorParser.parse(result.throwable))
                    }
                }
            }
        } else {
            return null
        }
    }

    private suspend inline fun KtorHttpResponse.toNetworkResult(expectedType: TypeInfo): NetworkResult<Any> {
        if (!status.isSuccess()) {
            return NetworkResult.error(errorParser.parse(status))
        }

        return runCatching {
            NetworkResult.success(expectedType.type.cast(body(expectedType)))
        }.getOrElse { throwable ->
            NetworkResult.error(errorParser.parse(throwable))
        }
    }
}
